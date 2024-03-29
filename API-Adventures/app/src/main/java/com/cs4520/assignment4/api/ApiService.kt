package com.cs4520.assignment4.api

import com.cs4520.assignment4.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // get the first 30 products
    @GET(RetrofitClient.ENDPOINT)
    suspend fun getProducts(): Response<Set<Product>>

    // get a page of products
    @GET(RetrofitClient.ENDPOINT)
    suspend fun getProductsPage(
        @Query("page") pageNumber: Int,
    ): Response<Set<Product>>
}
