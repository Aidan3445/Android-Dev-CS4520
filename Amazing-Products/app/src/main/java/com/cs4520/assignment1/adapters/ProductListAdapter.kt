package com.cs4520.assignment1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.R
import com.cs4520.assignment1.data.Product
import com.cs4520.assignment1.databinding.ElementBinding

class ProductListAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
    class ProductViewHolder(binding: ElementBinding) : RecyclerView.ViewHolder(binding.root) {
        val elementView: View = ElementBinding.bind(binding.root).element
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        // create a new view for a list element
        val inf = LayoutInflater.from(parent.context)
        val binding = ElementBinding.inflate(inf, parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        // return the number of products
        return products.size
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        val binding = ElementBinding.bind(holder.elementView)
        // get elements from dataset at the position and
        // set the text of the view with the element's values
        when (val product = products[position]) {
            is Product.Equipment -> {
                binding.elementName.text = product.name
                binding.elementPrice.text = product.price.toString()
                binding.elementImage.setImageResource(R.drawable.equipment)
                binding.element.setBackgroundResource(R.color.equipment_color)
            }
            is Product.Food -> {
                binding.elementName.text = product.name
                binding.elementDate.text = product.expirationDate
                binding.elementPrice.text = product.price.toString()
                binding.elementImage.setImageResource(R.drawable.food)
                binding.element.setBackgroundResource(R.color.food_color)
            }
        }
    }
}
