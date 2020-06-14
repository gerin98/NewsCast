package com.example.newscast.network

import com.example.newscast.network.model.NewsModel
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NewsService {

    @Headers(
        "Content-Type: application/json"
    )
    @POST("getArticles")
    suspend fun getArticles(@Body body: NewsRequestBody) : NewsModel

    @Headers(
        "Content-Type: application/json"
    )
    @POST("getArticles")
    suspend fun getSearchArticles(@Body body: SearchRequestBody) : NewsModel
}