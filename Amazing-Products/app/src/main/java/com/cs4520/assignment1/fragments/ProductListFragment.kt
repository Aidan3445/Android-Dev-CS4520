package com.cs4520.assignment1.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.R
import com.cs4520.assignment1.adapters.ProductListAdapter
import com.cs4520.assignment1.data.Product
import com.cs4520.assignment1.data.productsDataset

class ProductListFragment : Fragment(R.layout.product_list_fragment) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        // map products dataset to list of products
        val products: List<Product> =
            productsDataset.map {
                    p ->
                Product(p[0].toString(), p[1].toString(), p[2].toString(), p[3].toString().toInt())
            }

        // create adapter for product list and add to recycler view
        val adapter = ProductListAdapter(products)
        val recyclerView = view.findViewById<RecyclerView>(R.id.product_list)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        recyclerView.adapter = adapter
    }
}
