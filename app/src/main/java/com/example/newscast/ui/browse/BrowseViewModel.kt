package com.example.newscast.ui.browse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newscast.network.NewsRequestBody
import com.example.newscast.network.model.NewsModel
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.repository.FavouritesRepository
import com.example.newscast.repository.NewsRepository
import com.example.newscast.utils.state.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class BrowseViewModel: ViewModel(), KoinComponent {

    /** Koin Components **/
    private val repo by inject<NewsRepository>()
    private val favouritesRepository by inject<FavouritesRepository>()

    /** Live Data **/
    private val _newsLiveData = MutableLiveData<List<ResultsModel?>?>()
    val newsLiveData: LiveData<List<ResultsModel?>?>
        get() = _newsLiveData

    private val _errorMessageLiveData = MutableLiveData<Boolean>(false)
    val errorMessageLiveData: LiveData<Boolean>
        get() = _errorMessageLiveData

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData

    private val _showZeroCaseLiveData = MutableLiveData<Boolean>(true)
    val showZeroCaseLiveData: LiveData<Boolean>
        get() = _showZeroCaseLiveData

    private val _newsTopicLiveData = MutableLiveData<String?>()
    val newsTopic: LiveData<String?>
        get() = _newsTopicLiveData


    /* Variables */
    private var lastRequest: NewsRequestBody? = null

    /** Get News **/
    fun getInitialNews() {
        _progressBarVisibilityLiveData.value = true

        viewModelScope.launch {

            val body = NewsRequestBody(conceptUri = ConceptUri.NEWS.sort, articlesSortBy = ArticlesToSortBy.SOURCE_IMPORTANCE.sort)
            val response = repo.getNews(body)

            if (response.status == Status.SUCCESS) {
                lastRequest = body
                parseArticles(response.data)
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
                lastRequest = body
                parseArticles(response.data)
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
                lastRequest = body
                parseArticles(response.data)
                _newsTopicLiveData.postValue(title)
            } else if (response.status == Status.ERROR) {
                _errorMessageLiveData.postValue(true)
            }

            _progressBarVisibilityLiveData.postValue(false)
        }

    }

    fun refreshNews() {

        if (lastRequest == null) {
            getInitialNews()
            return
        }

        lastRequest?.let {
            _progressBarVisibilityLiveData.value = true

            viewModelScope.launch {
                val response = repo.getNews(it)

                if (response.status == Status.SUCCESS) {
                    parseArticles(response.data)
                } else if (response.status == Status.ERROR) {
                    _errorMessageLiveData.postValue(true)
                }

                _progressBarVisibilityLiveData.postValue(false)
            }
        }

    }

    private fun parseArticles(response: NewsModel?) {
        val results = response?.articles?.results
        if (results != null && results.isNotEmpty()) {
            _showZeroCaseLiveData.postValue(false)
            _newsLiveData.postValue(results)
        } else {
            noResults()
        }
    }

    private fun noResults() {
        _showZeroCaseLiveData.postValue(true)
        _newsLiveData.postValue(null)
        _errorMessageLiveData.postValue(true)
    }

    /* Database operations */
    fun addToDb(result: ResultsModel?, topic: String?) {
        Timber.e("inserting into db from Browse")
        var title: String? = null
        var body: String? = null
        var url: String? = null
        var imageUrl: String? = null
        var author: String? = null
        var uri: String? = null

        result?.let {
            title = it.title
            body = it.body
            url = it.url
            imageUrl = it.image
            author = it.source?.title
            uri = it.uri
        }

        viewModelScope.launch(Dispatchers.IO) {
            favouritesRepository.insertArticle(uri, title, body, url, imageUrl, author, topic)
        }
    }

}