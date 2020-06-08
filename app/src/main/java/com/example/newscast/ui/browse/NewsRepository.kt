package com.example.newscast.ui.browse

import com.example.newscast.network.NetworkResponseHelper
import com.example.newscast.network.NewsRequestBody
import com.example.newscast.network.NewsService
import com.example.newscast.network.model.NewsModel
import com.example.newscast.utils.state.Resource
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class NewsRepository: KoinComponent {

    val newsService by inject<NewsService>()
    val responseHelper by inject<NetworkResponseHelper>()

    suspend fun getNews(body: NewsRequestBody): Resource<NewsModel> {
        return try {
            val response = newsService.getArticles(body)
            return responseHelper.handleSuccess(response)
        } catch (e: Exception) {
            responseHelper.handleException(e)
        }
    }

}