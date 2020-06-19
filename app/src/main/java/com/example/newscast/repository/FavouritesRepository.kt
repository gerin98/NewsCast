package com.example.newscast.repository

import com.example.newscast.data.room.Articles
import com.example.newscast.data.room.ArticlesDao
import org.koin.dsl.module

val favouritesModule = module {
    factory { FavouritesRepository(get()) }
}

class FavouritesRepository(private val articlesDao: ArticlesDao) {

    suspend fun getAllFavourites(): List<Articles> {
        return articlesDao.getAllArticles()
    }

    suspend fun insertArticle(title: String? = null,
                              body: String? = null,
                              url: String? = null,
                              imageUrl: String? = null,
                              author: String? = null,
                              topic: String? = null) {
        val article = Articles(null, title, body, url, imageUrl, author, topic)
        articlesDao.insertItem(article)
    }

}