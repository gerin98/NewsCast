package com.example.newscast.ui.browse

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newscast.network.NewsRequestBody
import com.example.newscast.network.model.NewsModel
import com.example.newscast.utils.state.Status
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivityViewModel: ViewModel(), KoinComponent {

    // Koin Components
    private val repo by inject<NewsRepository>()

    // Live Data
    private val _newsLiveData = MutableLiveData<NewsModel>()
    val newsLiveData: LiveData<NewsModel>
        get() = _newsLiveData

    private val _errorMessageLiveData = MutableLiveData<Boolean>(false)
    val errorMessageLiveData: LiveData<Boolean>
        get() = _errorMessageLiveData

    // Data Binding Live Data
    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean>
        get() = _progressBarVisibility

    private val _newsTopic = MutableLiveData<String?>()
    val newsTopic: LiveData<String?>
        get() = _newsTopic

    fun getInitialNews() {
        _progressBarVisibility.value = true

        viewModelScope.launch {

            val body = NewsRequestBody(keyword = "News", articlesSortBy = ArticlesToSortBy.SOURCE_IMPORTANCE.sort)
            val response = repo.getNews(body)

            if (response.status == Status.SUCCESS) {
                _newsLiveData.postValue(response.data)
                _newsTopic.postValue("Breaking News")
            } else if (response.status == Status.ERROR) {
                // send failure toast here
                _errorMessageLiveData.postValue(true)
            }

            _progressBarVisibility.postValue(false)
        }

    }

    fun getLatestNews() {
        _progressBarVisibility.value = true

        viewModelScope.launch {

            val body = NewsRequestBody(keyword = "News", articlesSortBy = "sourceImportance")
            val response = repo.getNews(body)

            if (response.status == Status.SUCCESS) {
                _newsLiveData.postValue(response.data)
                _newsTopic.postValue("Breaking News")
            } else if (response.status == Status.ERROR) {
                // send failure toast here
                _errorMessageLiveData.postValue(true)
            }

            _progressBarVisibility.postValue(false)
        }

    }

    fun getNewsForTopic(topic: SearchLinks) {
        _progressBarVisibility.value = true

        var keyword = ""
        var articlesSortBy = ""
        var title = ""
        when(topic) {
            is SearchLinks.World -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Us -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Politics -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Business -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Tech -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Science -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Sports -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Travel -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Culture -> {
                keyword = topic.keyword
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            else -> {
                keyword = "News"
                articlesSortBy = ArticlesToSortBy.DATE.sort
                title = "Breaking News"
            }
        }

        viewModelScope.launch {

            val body = NewsRequestBody(keyword = keyword, articlesSortBy = articlesSortBy)
            val response = repo.getNews(body)

            if (response.status == Status.SUCCESS) {
                _newsLiveData.postValue(response.data)
                _newsTopic.postValue(title)
            } else if (response.status == Status.ERROR) {
                // send failure toast here
                _errorMessageLiveData.postValue(true)
            }

            _progressBarVisibility.postValue(false)
        }

    }

}