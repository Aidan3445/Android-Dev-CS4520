package com.cs4520.assignment4.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.R
import com.cs4520.assignment4.adapters.ProductListAdapter
import com.cs4520.assignment4.api.Api
import com.cs4520.assignment4.database.dao.ProductDao
import com.cs4520.assignment4.database.entities.ProductEntity
import com.cs4520.assignment4.databinding.ProductListFragmentBinding
import com.cs4520.assignment4.models.Product
import com.cs4520.assignment4.models.ProductRepository
import com.cs4520.assignment4.viewmodels.ProductListViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class ProductListFragment : Fragment(R.layout.product_list_fragment) {
    private lateinit var binding: ProductListFragmentBinding
    private lateinit var adapter: ProductListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var productDao: ProductDao
    private lateinit var viewModel: ProductListViewModel
    private var pageNumber = 1

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProductListFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]

        // establish the recycler view with the adapter an empty set of products
        adapter = ProductListAdapter(emptySet())
        recyclerView = binding.productList
        binding.productList.adapter = adapter
        binding.productList.layoutManager = LinearLayoutManager(view.context)

        // add page controls
        binding.paginationControls.previousButton.setOnClickListener {
            pageNumber--
            oldfetch()
        }
        binding.paginationControls.nextButton.setOnClickListener {
            pageNumber++
            oldfetch()
        }

        // add reload button
        binding.reloadButton.setOnClickListener {
            oldfetch()
        }

        // make api initial call to get products
        oldfetch()
    }

    private fun oldfetch() {
        MainScope().launch {
            // update UI for loading
            binding.progressBar.visibility = View.VISIBLE
            binding.noProductsMessage.visibility = View.GONE
            binding.reloadButton.visibility = View.GONE
            binding.productList.visibility = View.GONE

            // update pagination controls
            binding.paginationControls.pageNumber.text = pageNumber.toString()
            binding.paginationControls.nextButton.isEnabled = pageNumber < 4
            binding.paginationControls.previousButton.isEnabled = pageNumber > 1

            try {
                fetchProductsFromAPI()
            } catch (e: Exception) {
                Log.e("ProductListFragment", "Error fetching products from API: $e")
                fetchProductsFromDB()
            } finally {
                binding.progressBar.visibility = View.GONE
            }

            try {
                val products = ProductRepository(productDao).fetch(pageNumber)
                Log.d("ProductListREPOSITORY", "Fetched products: $products")
            } catch (e: ProductRepository.FetchException) {
                Log.e("ProductListREPOSITORY", "Error fetching products: $e")
            }
        }
    }

    private suspend fun fetchProductsFromAPI() {
        val response = Api.apiService.getProductsPage(pageNumber)

        // handle response
        if (response.isSuccessful) {
            val products = response.body() ?: emptySet()

            if (products.isEmpty()) {
                binding.noProductsMessage.visibility = View.VISIBLE
                binding.reloadButton.visibility = View.VISIBLE
            } else {
                // set up recycler view
                adapter.updateProducts(products)
                recyclerView.layoutManager = LinearLayoutManager(view?.context)
                recyclerView.adapter = adapter
                binding.productList.visibility = View.VISIBLE

                // save products to db
                products.forEach { product ->
                    val entity =
                        when (product) {
                            is Product.Equipment -> {
                                ProductEntity(
                                    type = "Equipment",
                                    name = product.name,
                                    price = product.price,
                                    pageNumber = pageNumber,
                                )
                            }

                            is Product.Food -> {
                                ProductEntity(
                                    type = "Food",
                                    name = product.name,
                                    price = product.price,
                                    expiryDate = product.expiryDate,
                                    pageNumber = pageNumber,
                                )
                            }
                        }
                    productDao.insertProduct(entity)
                }
            }
        } else {
            val errorObj = response.errorBody()?.string()

            val err =
                try {
                    val json = errorObj?.let { JSONObject(it) }
                    json?.getString("message")
                } catch (e: Exception) {
                    "Unknown error"
                }

            binding.noProductsMessage.text = err
            binding.noProductsMessage.visibility = View.VISIBLE
            binding.reloadButton.visibility = View.VISIBLE
        }
    }

    private suspend fun fetchProductsFromDB() {
        val productEntities = productDao.getProductsByPage(pageNumber)

        if (productEntities.isEmpty()) {
            binding.noProductsMessage.visibility = View.VISIBLE
            binding.reloadButton.visibility = View.VISIBLE
        } else {
            // map entities to products
            val products =
                productEntities.map { entity ->
                    when (entity.type) {
                        "Equipment" -> {
                            Product.Equipment(entity.name, entity.price)
                        }
                        "Food" -> {
                            Product.Food(entity.name, entity.expiryDate!!, entity.price)
                        }
                        else -> throw IllegalArgumentException("Unknown product type: ${entity.type}")
                    }
                }.toSet()

            // set up recycler view
            adapter.updateProducts(products)
            recyclerView.layoutManager = LinearLayoutManager(view?.context)
            recyclerView.adapter = adapter
            binding.productList.visibility = View.VISIBLE
        }
    }
}
