package com.cs4520.assignment4.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val BASE_URL: String = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/"
    const val ENDPOINT: String = "prod/"
    const val PAGE: String = "?page={pn}"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object Api {
    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}
