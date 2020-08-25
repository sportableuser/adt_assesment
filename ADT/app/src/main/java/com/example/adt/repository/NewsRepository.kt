package com.example.adt.repository

import com.example.adt.network.NetworkService

class NewsRepository {
    suspend fun getArticles() = NetworkService.getRestApi().getArticles()
}