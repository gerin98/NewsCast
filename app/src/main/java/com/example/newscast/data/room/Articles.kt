package com.example.newscast.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles_table")
data class Articles(
    @PrimaryKey val uri: String = "",
    val title: String? = null,
    val body: String? = null,
    val url: String? = null,
    val imageUrl: String? = null,
    val author: String? = null,
    val topic: String? = null
)
