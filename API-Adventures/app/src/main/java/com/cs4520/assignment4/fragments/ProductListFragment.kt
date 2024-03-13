package com.cs4520.assignment4.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.R
import com.cs4520.assignment4.adapters.ProductListAdapter
import com.cs4520.assignment4.database.ProductDatabase
import com.cs4520.assignment4.database.dao.ProductDao
import com.cs4520.assignment4.databinding.ProductListFragmentBinding
import com.cs4520.assignment4.models.ProductRepository
import com.cs4520.assignment4.viewmodels.ProductListViewModel

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
        viewModel =
            ProductListViewModel(
                ProductRepository(
                    ProductDatabase.getInstance(requireContext()).productDao(),
                ),
            )

        // establish the recycler view with the adapter an empty set of products
        adapter = ProductListAdapter(emptySet())
        recyclerView = binding.productList
        binding.productList.adapter = adapter
        binding.productList.layoutManager = LinearLayoutManager(view.context)

        // observe live data
        viewModel.products.observe(viewLifecycleOwner) { products ->
            // update and show products
            adapter.updateProducts(products)
            binding.productList.visibility = View.VISIBLE
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            // show error message
            binding.noProductsMessage.text = message
            binding.noProductsMessage.visibility = View.VISIBLE
            binding.reloadButton.visibility = View.VISIBLE
        }
        viewModel.isFetching.observe(viewLifecycleOwner) { isFetching ->
            if (isFetching) {
                // show loading
                binding.progressBar.visibility = View.VISIBLE
                // hide other views
                binding.productList.visibility = View.GONE
                binding.noProductsMessage.visibility = View.GONE
                binding.reloadButton.visibility = View.GONE
            } else {
                // hide loading
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.pageNumber.observe(viewLifecycleOwner) { page ->
            // update pagination controls
            binding.paginationControls.pageNumber.text = page.toString()
        }
        viewModel.hasPrev.observe(viewLifecycleOwner) { hasPrev ->
            // update pagination controls
            binding.paginationControls.previousButton.isEnabled = hasPrev
        }
        viewModel.hasNext.observe(viewLifecycleOwner) { hasNext ->
            // update pagination controls
            binding.paginationControls.nextButton.isEnabled = hasNext
        }

        // add page controls
        binding.paginationControls.previousButton.setOnClickListener {
            viewModel.prevPage()
        }
        binding.paginationControls.nextButton.setOnClickListener {
            viewModel.nextPage()
        }
        binding.reloadButton.setOnClickListener {
            viewModel.fetchProducts()
        }

        // make api initial call to get products
        viewModel.fetchProducts()
    }
}
