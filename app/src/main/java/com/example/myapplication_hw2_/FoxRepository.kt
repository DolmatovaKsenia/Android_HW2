package com.example.myapplication_hw2_

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoxRepository(private val apiService: FoxApiService) {

    fun getRandomFoxImage(): LiveData<FoxDataClass?> {
        val foxImageLiveData = MutableLiveData<FoxDataClass?>()

        apiService.getFoxImage().enqueue(object : Callback<FoxDataClass> {
            override fun onResponse(call: Call<FoxDataClass>, response: Response<FoxDataClass>) {
                if (response.isSuccessful) {
                    foxImageLiveData.postValue(response.body())
                } else {
                    foxImageLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<FoxDataClass>, t: Throwable) {
                foxImageLiveData.postValue(null)
            }
        })

        return foxImageLiveData
    }
}