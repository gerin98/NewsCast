package com.example.newscast.network.model

import com.squareup.moshi.Json

data class SourceModel (
    @field:Json(name = "uri") val uri: String?,
    @field:Json(name = "dataType") val dataType: String?,
    @field:Json(name = "title") val title: String?
)