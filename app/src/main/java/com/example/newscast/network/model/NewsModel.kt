package com.example.newscast.network.model

import com.squareup.moshi.Json

data class NewsModel(
    @field:Json(name = "articles") val articles: ArticleModel? = null
)
