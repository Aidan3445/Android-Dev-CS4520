package com.cs4520.assignment5.workManager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cs4520.assignment5.api.Api
import com.cs4520.assignment5.database.ProductDatabase
import com.cs4520.assignment5.database.dao.ProductDao
import com.cs4520.assignment5.database.entities.ProductEntity
import com.cs4520.assignment5.models.Product

class ProductRefreshWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(
        context,
        params,
    ) {
    private val productDao: ProductDao

    init {
        productDao = ProductDatabase.init(context).productDao()
    }

    override suspend fun doWork(): Result {
        Log.d("Worker", "Im working")
        try {
            // get random products
            val response = Api.apiService.getRandomProducts()

            // handle response
            if (response.isSuccessful) {
                val products = response.body() ?: emptySet()
                insertProductsIntoDB(products, 1)
                Log.d("Worker", "That shit working ${products.size}")
                return Result.success()
            }
        } catch (_: Exception) {
            return Result.retry()
        }

        return Result.failure()
    }

    private suspend fun insertProductsIntoDB(
        products: Set<Product>,
        pageNumber: Int,
    ) {
        // save products to db
        products.forEach { product ->
            // create entity from product
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

            // insert entity into db
            productDao.insertProduct(entity)
        }
    }
}
