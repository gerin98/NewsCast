package com.example.newscast.network.model

import com.squareup.moshi.Json

data class ResultsModel(
    @field:Json(name = "uri") val uri: String?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "date") val date: String?,
    @field:Json(name = "image") val image: String?,
    @field:Json(name = "body") val body: String?,
    @field:Json(name = "source") val source: SourceModel?
)