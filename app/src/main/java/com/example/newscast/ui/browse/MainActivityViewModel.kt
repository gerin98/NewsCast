package com.example.newscast.ui.browse

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

    /** Koin Components **/
    private val repo by inject<NewsRepository>()

    /** Live Data **/
    private val _newsLiveData = MutableLiveData<NewsModel>()
    val newsLiveData: LiveData<NewsModel>
        get() = _newsLiveData

    private val _errorMessageLiveData = MutableLiveData<Boolean>(false)
    val errorMessageLiveData: LiveData<Boolean>
        get() = _errorMessageLiveData

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData

    private val _newsTopicLiveData = MutableLiveData<String?>()
    val newsTopic: LiveData<String?>
        get() = _newsTopicLiveData

    /** Get News **/
    fun getInitialNews() {
        _progressBarVisibilityLiveData.value = true

        viewModelScope.launch {

            val body = NewsRequestBody(conceptUri = ConceptUri.NEWS.sort, articlesSortBy = ArticlesToSortBy.SOURCE_IMPORTANCE.sort)
            val response = repo.getNews(body)

            if (response.status == Status.SUCCESS) {
                _newsLiveData.postValue(response.data)
                _newsTopicLiveData.postValue("Breaking News")
            } else if (response.status == Status.ERROR) {
                _errorMessageLiveData.postValue(true)
            }

            _progressBarVisibilityLiveData.postValue(false)
        }

    }

    fun getLatestNews() {
        _progressBarVisibilityLiveData.value = true

        viewModelScope.launch {

            val body = NewsRequestBody(conceptUri = ConceptUri.NEWS.sort, articlesSortBy = ArticlesToSortBy.SOURCE_IMPORTANCE.sort)
            val response = repo.getNews(body)

            if (response.status == Status.SUCCESS) {
                _newsLiveData.postValue(response.data)
                _newsTopicLiveData.postValue("Breaking News")
            } else if (response.status == Status.ERROR) {
                _errorMessageLiveData.postValue(true)
            }

            _progressBarVisibilityLiveData.postValue(false)
        }

    }

    fun getNewsForTopic(topic: SearchLinks) {
        _progressBarVisibilityLiveData.value = true

        var conceptUri = ""
        var articlesSortBy = ""
        var title = ""
        when(topic) {
            is SearchLinks.World -> {
                conceptUri = topic.conceptUri
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Us -> {
                conceptUri = topic.conceptUri
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Politics -> {
                conceptUri = topic.conceptUri
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Business -> {
                conceptUri = topic.conceptUri
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Tech -> {
                conceptUri = topic.conceptUri
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Science -> {
                conceptUri = topic.conceptUri
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Sports -> {
                conceptUri = topic.conceptUri
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Travel -> {
                conceptUri = topic.conceptUri
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            is SearchLinks.Culture -> {
                conceptUri = topic.conceptUri
                articlesSortBy = topic.articlesSortBy
                title = topic.title
            }
            else -> {
                conceptUri = ConceptUri.NEWS.sort
                articlesSortBy = ArticlesToSortBy.SOURCE_IMPORTANCE.sort
                title = "Breaking News"
            }
        }

        viewModelScope.launch {

            val body = NewsRequestBody(conceptUri = conceptUri, articlesSortBy = articlesSortBy)
            val response = repo.getNews(body)

            if (response.status == Status.SUCCESS) {
                _newsLiveData.postValue(response.data)
                _newsTopicLiveData.postValue(title)
            } else if (response.status == Status.ERROR) {
                _errorMessageLiveData.postValue(true)
            }

            _progressBarVisibilityLiveData.postValue(false)
        }

    }

}