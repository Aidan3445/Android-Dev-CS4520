package com.cs4520.assignment5.composables.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cs4520.assignment5.models.Product

@Preview(showBackground = true)
@Composable
fun ProductList(
    list: Set<Product> =
        setOf(
            Product.Food("Apple", "8/12/2003", 1.4f),
            Product.Equipment("Hammer", 1.4f),
            Product.Food("Banana", "12/12/2000", 0.79f),
            Product.Equipment("Screwdriver", 2.4f),
            Product.Food("20 Oranges", "3/3/2024", 10f),
            Product.Equipment("Treadmill", 1000f),
            Product.Food("Pizza", "12/12/2022", 5.99f),
            Product.Equipment("Dumbbell", 50f),
            Product.Food("Pasta", "3/3/2023", 3.99f),
            Product.Equipment("Bicycle", 200f),
            Product.Food("Bread", "8/12/2023", 2.99f),
            Product.Equipment("Laptop", 800f),
            Product.Food("Milk", "12/12/2023", 1.99f),
            Product.Equipment("Headphones", 100f),
            Product.Food("Cheese", "3/3/2023", 4.99f),
            Product.Equipment("Monitor", 300f),
            Product.Food("Eggs", "8/12/2023", 1.99f),
            Product.Equipment("Keyboard", 50f),
            Product.Food("Cereal", "12/12/2023", 3.99f),
            Product.Equipment("Mouse", 20f),
            Product.Food("Chips", "3/3/2023", 2.99f),
            Product.Equipment("Chair", 50f),
        ),
) {
    // use lazy column for scrolling products
    LazyColumn {
        items(list.size) { index ->
            ProductElement(list.elementAt(index))
        }
    }
}
