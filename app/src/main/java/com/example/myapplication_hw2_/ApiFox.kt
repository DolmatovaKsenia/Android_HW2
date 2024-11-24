package com.example.myapplication_hw2_

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFox {
    private const val BASE_URL = "https://randomfox.ca/"

    val apiService: FoxApiService by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(FoxApiService::class.java)
    }
}