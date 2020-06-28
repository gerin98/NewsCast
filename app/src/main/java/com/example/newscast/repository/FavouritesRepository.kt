package com.example.newscast.repository

import androidx.lifecycle.LiveData
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

    fun getAllFavouritesLiveData(): LiveData<List<Articles>?> {
        return articlesDao.getAllArticlesLiveData()
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

    suspend fun deleteArticleByUris(uris: Array<String>) {
        articlesDao.deleteByUris(uris)
    }

    suspend fun isFavourited(uri: String?): Boolean {
        if (uri == null) {
            return false
        }

        val uris = articlesDao.getArticleUriByUri(uri)
        return uris.isEmpty().not()
    }

    suspend fun clearFavourites() {
        articlesDao.clearFavourites()
    }

    suspend fun getArticlesByUri(uri: String?): List<Articles?> {
        return if (uri != null) {
            articlesDao.getArticlesByUri(uri)
        } else {
            emptyList()
        }
    }

}