package com.example.newscast.network.model

import com.squareup.moshi.Json
import java.util.*

data class NewsModel(
    @field:Json(name = "articles") val articles: ArticleModel?
)
