package com.example.newscast.network

import com.example.newscast.network.model.ArticleModel
import com.example.newscast.network.model.NewsModel
import retrofit2.http.*

interface NewsService {

    @Headers(
        "Content-Type: application/json"
    )
    @POST("getArticles")
    suspend fun getArticles(@Body body: NewsRequestBody) : NewsModel
}