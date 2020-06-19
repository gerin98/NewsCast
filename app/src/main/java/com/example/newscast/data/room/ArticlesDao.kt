package com.example.newscast.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface ArticlesDao {

    @Insert(onConflict = IGNORE)
    fun insert(articles: Articles)

    @Query("SELECT * from articles_table")
    fun getAllArticles(): List<Articles>

}