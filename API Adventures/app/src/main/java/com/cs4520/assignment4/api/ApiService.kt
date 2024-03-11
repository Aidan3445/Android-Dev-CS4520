package com.cs4520.assignment4.api

import com.cs4520.assignment4.data.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // get the first 30 products
    @GET(RetrofitClient.ENDPOINT)
    suspend fun getProducts(): Call<Product>

    // get a page of products
    @GET(RetrofitClient.ENDPOINT + RetrofitClient.PAGE)
    suspend fun getProductsPage(
        @Path("pn") pageNumber: Int,
    ): Call<Product>
}
