package com.example.adt.network

import com.example.adt.modal.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestAPI {
    @GET("top-headlines")
    suspend fun getArticles(@Query("country") country: String = "US",
                            @Query("apiKey") apiKey: String = "1437be957ba74f9e93cf1688a28a05ac"): Response<ArticleResponse>

}