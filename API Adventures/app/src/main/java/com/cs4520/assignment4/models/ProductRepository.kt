package com.cs4520.assignment4.models

import android.util.Log
import com.cs4520.assignment4.api.Api
import com.cs4520.assignment4.database.ProductDatabase
import com.cs4520.assignment4.database.dao.ProductDao
import com.cs4520.assignment4.database.entities.ProductEntity
import org.json.JSONObject
import java.net.UnknownHostException

class ProductRepository(private val productDao: ProductDao) {
    // constructor
    constructor(db: ProductDatabase) : this(db.productDao()) {
    }

    class FetchException(message: String) : Exception(message)

    // fetch products from api or db
    // this will either return products (including empty set) or throw an exception
    suspend fun fetch(pageNumber: Int): Set<Product> {
        val products =
            try {
                fetchProductsFromAPI(pageNumber)
            } catch (e: UnknownHostException) {
                Log.d("ProductRepository", "No internet, fetching from db")
                fetchProductsFromDB(pageNumber)
            }

        return products
    }

    private suspend fun fetchProductsFromAPI(pageNumber: Int): Set<Product> {
        // fetch products from api
        // throws unknown host exception if there is no internet
        val response = Api.apiService.getProductsPage(pageNumber)

        // handle response
        if (response.isSuccessful) {
            val products = response.body() ?: emptySet()

            // save products to db and return
            insertProductsIntoDB(products, pageNumber)
            return products
        } else {
            val errorObj = response.errorBody()?.string()

            // get error message
            val err =
                try {
                    val json = errorObj?.let { JSONObject(it) }
                    json?.getString("message")
                } catch (e: Exception) {
                    e.message
                }

            // throw custom error type for better handling
            throw FetchException(err ?: "Unknown error")
        }
    }

    private suspend fun fetchProductsFromDB(pageNumber: Int): Set<Product> {
        val productEntities = productDao.getProductsByPage(pageNumber)

        if (productEntities.isEmpty()) {
            // handle empty products
            throw FetchException("No products available")
        }

        // map entities to products
        return productEntities.map { entity ->
            when (entity.type) {
                "Equipment" -> {
                    Product.Equipment(entity.name, entity.price)
                }
                "Food" -> {
                    Product.Food(entity.name, entity.expiryDate!!, entity.price)
                }
                else -> throw FetchException("Unknown product type: ${entity.type}")
            }
        }.toSet()
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
