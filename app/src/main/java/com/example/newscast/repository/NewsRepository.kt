package com.example.newscast.repository

import com.example.newscast.network.NetworkResponseHelper
import com.example.newscast.network.NewsRequestBody
import com.example.newscast.network.NewsService
import com.example.newscast.network.SearchRequestBody
import com.example.newscast.network.model.NewsModel
import com.example.newscast.utils.state.Resource
import org.koin.dsl.module
import java.lang.Exception

val newsModule = module {
    factory { NewsRepository(get(), get()) }
}

class NewsRepository(private val api: NewsService, private val responseHelper: NetworkResponseHelper) {

    suspend fun getNews(body: NewsRequestBody): Resource<NewsModel> {
        return try {
            val response = api.getArticles(body)
            return responseHelper.handleSuccess(response)
        } catch (e: Exception) {
            responseHelper.handleException(e)
        }
    }

    suspend fun searchNews(body: SearchRequestBody): Resource<NewsModel> {
        return try {
            val response = api.getSearchArticles(body)
            return responseHelper.handleSuccess(response)
        } catch (e: Exception) {
            responseHelper.handleException(e)
        }
    }

}