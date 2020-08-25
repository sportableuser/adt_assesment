package com.example.adt.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkService {
    private val restAPI: RestAPI = createAPI()
    private fun createAPI(): RestAPI {
        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(RestAPI::class.java)
    }

    fun getRestApi() = restAPI


}