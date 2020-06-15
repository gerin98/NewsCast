package com.example.newscast.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles_table")
data class Articles(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "article_title") val title: String?,
    @ColumnInfo(name = "article_body") val body: String?
)