package com.cs4520.assignment1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.R
import com.cs4520.assignment1.adapters.ProductListAdapter
import com.cs4520.assignment1.data.Product
import com.cs4520.assignment1.data.productsDataset

class ProductListFragment : Fragment(R.layout.product_list_fragment) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

        // map products dataset to list of products
        val products: List<Product> =
            productsDataset.map {
                    p ->
                Product(p[0].toString(), p[1].toString(), p[2].toString(), p[3].toString().toInt())
            }

        for (p in products) Log.println(Log.INFO, "Product", p.toString())

        // create adapter for product list and add to recycler view
        val adapter: ProductListAdapter = ProductListAdapter(products)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.product_list)
        recyclerView?.adapter = adapter
    }
}
