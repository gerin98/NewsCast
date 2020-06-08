package com.example.newscast.ui.browse

import com.example.newscast.network.NetworkResponseHelper
import com.example.newscast.network.NewsRequestBody
import com.example.newscast.network.NewsService
import com.example.newscast.network.model.NewsModel
import com.example.newscast.utils.state.Resource
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.dsl.module
import java.lang.Exception

val newsModule = module {
    factory { NewsRepository(get(), get()) }
}

class NewsRepository(private val service: NewsService, private val responseHelper: NetworkResponseHelper) {

    suspend fun getNews(body: NewsRequestBody): Resource<NewsModel> {
        return try {
            val response = service.getArticles(body)
            return responseHelper.handleSuccess(response)
        } catch (e: Exception) {
            responseHelper.handleException(e)
        }
    }

}