package com.example.newscast.network.model

import com.squareup.moshi.Json

data class ArticleModel(
    @field:Json(name = "totalResults") val totalResults: String?,
    @field:Json(name = "page") val page: String?,
    @field:Json(name = "count") val count: String?,
    @field:Json(name = "pages") val pages: String?,
    @field:Json(name = "results") val results: List<ResultsModel?>?
)