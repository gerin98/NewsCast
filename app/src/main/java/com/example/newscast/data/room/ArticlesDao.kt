package com.example.newscast.data.room

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM articles_table")
    fun getAllArticles(): List<Articles>

    @Query("SELECT * FROM articles_table")
    fun getAllArticlesLiveData(): LiveData<List<Articles>?>

    @Query("SELECT uri FROM articles_table WHERE uri = :uri")
    fun getArticleUriByUri(uri: String): List<String?>

    @Query("SELECT * FROM articles_table WHERE uri = :uri")
    fun getArticlesByUri(uri: String): List<Articles?>

    @Query("DELETE FROM articles_table WHERE uri = :uri")
    fun deleteByUri(uri: String)

    @Query("DELETE FROM articles_table WHERE uri IN (:uriList)")
    fun deleteByUris(uriList: Array<String>)

    @Query("DELETE FROM articles_table")
    fun clearFavourites()

}