package com.cs4520.assignment1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.R
import com.cs4520.assignment1.data.Product

class ProductListAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
    class ProductViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val elementView: View = view.findViewById(R.id.element)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        // create a new view for a list element
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.element, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        // return the number of products
        return products.size
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        // get elements from dataset at the position and
        // set the text of the view with the element's values
        val product = products[position]
        holder.elementView.findViewById<TextView>(R.id.element_name).text = product.name
        holder.elementView.findViewById<TextView>(R.id.element_date).text = product.date
        holder.elementView.findViewById<TextView>(R.id.element_price).text = product.price

        // set image and background from type
        val image: Int
        val background: Int
        if (product.type == "Food") {
            image = R.drawable.food
            background = R.color.food_color
        } else {
            image = R.drawable.equipment
            background = R.color.equipment_color
        }
        holder.elementView.findViewById<ImageView>(R.id.element_image).setBackgroundResource(image)
        holder.elementView.setBackgroundResource(background)
    }
}
