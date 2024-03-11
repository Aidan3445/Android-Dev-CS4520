package com.cs4520.assignment4.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs4520.assignment4.R
import com.cs4520.assignment4.adapters.ProductListAdapter
import com.cs4520.assignment4.data.Product
import com.cs4520.assignment4.data.productsDataset
import com.cs4520.assignment4.databinding.ProductListFragmentBinding

class ProductListFragment : Fragment(R.layout.product_list_fragment) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = ProductListFragmentBinding.bind(view)

        // map products dataset to list of products
        val products: List<Product> =
            productsDataset.map {
                    p ->
                when (p[1].toString().lowercase()) {
                    "equipment" -> Product.Equipment(p[0].toString(), p[3].toString().toInt())
                    "food" -> Product.Food(p[0].toString(), p[2].toString(), p[3].toString().toInt())
                    else -> throw IllegalArgumentException("Invalid product type: ${p[1]}")
                }
            }

        // create adapter for product list and add to recycler view
        val adapter = ProductListAdapter(products)
        val recyclerView = binding.productList

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        recyclerView.adapter = adapter
    }
}
