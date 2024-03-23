package com.cs4520.assignment5.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs4520.assignment5.models.Product

@Preview(showBackground = true)
@Composable
fun ProductElement(
    product: Product =
        Product.Food("Apple", "8/12/2003", 1.4f),
) {
    // determine image and content description
    val name: String
    val date: String
    val price: String
    val image: Int
    val contentDescription: String
    val color: Int
    when (product) {
        is Product.Food -> {
            name = product.name
            date = product.expiryDate
            price = "$%.2f".format(product.price)
            image = com.cs4520.assignment5.R.drawable.food
            contentDescription = "Food"
            color = com.cs4520.assignment5.R.color.food_color
        }
        is Product.Equipment -> {
            name = product.name
            date = ""
            price = "$%.2f".format(product.price)
            image = com.cs4520.assignment5.R.drawable.equipment
            contentDescription = "Equipment"
            color = com.cs4520.assignment5.R.color.equipment_color
        }
    }

    Row(
        modifier =
            Modifier
                .requiredHeight(100.dp)
                .fillMaxWidth()
                .background(colorResource(id = color))
                .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // product image
        Image(
            painter = painterResource(id = image),
            contentDescription = contentDescription,
            modifier =
                Modifier.clip(shape = RectangleShape)
                    .requiredWidth(80.dp)
                    .requiredHeight(80.dp)
                    .scale(1.4f),
        )

        // detail column
        Column(
            modifier = Modifier.fillMaxSize().padding(5.dp, 0.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            // product name
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
            )

            // product date
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium,
            )

            // product price
            Text(
                text = price,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}
