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

    fun getNews() {

        viewModelScope.launch {

            val service = NewsApi.getRetrofitInstance().create(NewsService::class.java)
            val body = NewsRequestBody(keyword = "Headline", articlesSortBy = "rel")

            val news = service.getArticles(body)

            if (news.articles == null) {
                Log.e("gerin", "null articles")
            }

            _newsLiveData.value = news
        }

    }


}