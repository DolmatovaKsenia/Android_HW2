package com.example.myapplication_hw2_

import retrofit2.Call
import retrofit2.http.GET

interface FoxApiService {
    @GET("floof/")
    fun getFoxImage(): Call<FoxDataClass>
}