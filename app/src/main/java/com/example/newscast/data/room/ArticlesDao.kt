package com.example.newscast.data.room

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface ArticlesDao {

    @Insert(onConflict = IGNORE)
    fun insertItem(article: Articles)

    @Insert(onConflict = IGNORE)
    fun insertItems(vararg articles: Articles)

    @Delete
    fun deleteItem(article: Articles)

    @Delete
    fun deleteItems(vararg articles: Articles)

    @Update
    fun updateItem(article: Articles)

    @Update
    fun updateItems(vararg articles: Articles)

    @Query("SELECT * from articles_table")
    fun getAllArticles(): List<Articles>

}