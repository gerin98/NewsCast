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

    suspend fun insertArticle(uri: String? = null,
                              title: String? = null,
                              body: String? = null,
                              url: String? = null,
                              imageUrl: String? = null,
                              author: String? = null,
                              topic: String? = null) {
        uri?.let {
            val article = Articles(uri, title, body, url, imageUrl, author, topic)
            articlesDao.insertItem(article)
        }
    }

    suspend fun deleteArticleByUri(uri: String) {
        articlesDao.deleteByUri(uri)
    }

    suspend fun isFavourited(uri: String?): Boolean {
        if (uri == null) {
            return false
        }

        val uris = articlesDao.getArticlesByUri(uri)
        return uris.isEmpty().not()
    }

}