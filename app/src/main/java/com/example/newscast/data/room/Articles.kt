package com.example.newscast.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles_table")
data class Articles(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "article_uri") val uri: String? = null,
    @ColumnInfo(name = "article_title") val title: String? = null,
    @ColumnInfo(name = "article_body") val body: String? = null,
    @ColumnInfo(name = "article_url") val url: String? = null,
    @ColumnInfo(name = "article_image_url") val imageUrl: String? = null,
    @ColumnInfo(name = "article_author") val author: String? = null,
    @ColumnInfo(name = "article_topic") val topic: String? = null
)
