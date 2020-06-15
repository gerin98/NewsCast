package com.example.newscast.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ArticlesDao {

    @Query("SELECT * from articles_table")
    fun getAllArticles(): List<Articles>

}