package com.example.newscast.ui.browse

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newscast.network.NewsApi
import com.example.newscast.network.NewsRequestBody
import com.example.newscast.network.NewsService
import com.example.newscast.network.model.NewsModel
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {

    private val _newsLiveData = MutableLiveData<NewsModel>()
    val newsLiveData: LiveData<NewsModel>
        get() = _newsLiveData

    private val _progressBarVisibility = MutableLiveData<Boolean>(false)
    val progressBarVisibility: LiveData<Boolean>
        get() = _progressBarVisibility

    fun getNews() {
        _progressBarVisibility.value = true

        viewModelScope.launch {

            val service = NewsApi.getRetrofitInstance().create(NewsService::class.java)
            val body = NewsRequestBody(keyword = "News", articlesSortBy = ArticlesToSortBy.SOURCE_IMPORTANCE.sort)

            val news = service.getArticles(body)

            if (news.articles == null) {
                Log.e("gerin", "null articles")
            }

            _newsLiveData.postValue(news)
            _progressBarVisibility.postValue(false)
        }

    }

    fun getLatestNews() {
        _progressBarVisibility.value = true

        viewModelScope.launch {

            val service = NewsApi.getRetrofitInstance().create(NewsService::class.java)
            val body = NewsRequestBody(keyword = "News", articlesSortBy = "sourceImportance")

            val news = service.getArticles(body)

            if (news.articles == null) {
                Log.e("gerin", "null articles")
            }

            _newsLiveData.postValue(news)
            _progressBarVisibility.postValue(false)
        }

    }

    fun getNewsForTopic(topic: SearchLinks) {
        _progressBarVisibility.value = true

        var keyword = ""
        var articlesSortBy = ""
        when(topic) {
            is SearchLinks.World -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
            }
            is SearchLinks.Us -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
            }
            is SearchLinks.Politics -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
            }
            is SearchLinks.Business -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
            }
            is SearchLinks.Tech -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
            }
            is SearchLinks.Science -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
            }
            is SearchLinks.Sports -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
            }
            is SearchLinks.Travel -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
            }
            is SearchLinks.Culture -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
            }
            else -> {
                keyword = "News"
                articlesSortBy = ArticlesToSortBy.DATE.sort
            }
        }

        viewModelScope.launch {

            val service = NewsApi.getRetrofitInstance().create(NewsService::class.java)
            val body = NewsRequestBody(keyword = keyword, articlesSortBy = articlesSortBy)

            val news = service.getArticles(body)

            if (news.articles == null) {
                Log.e("gerin", "null articles")
            }

            _newsLiveData.postValue(news)
            _progressBarVisibility.postValue(false)
        }

    }

}