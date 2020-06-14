package com.example.newscast.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newscast.network.SearchRequestBody
import com.example.newscast.network.model.NewsModel
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.repository.NewsRepository
import com.example.newscast.utils.state.Status
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class SearchViewModel : ViewModel(), KoinComponent {

    /** Koin Components **/
    private val repo by inject<NewsRepository>()

    /** Search LiveData **/
    private val _searchLiveData = MutableLiveData<List<ResultsModel?>?>()
    val searchLiveData: LiveData<List<ResultsModel?>?>
        get() = _searchLiveData

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData

    private val _showZeroCaseLiveData = MutableLiveData<Boolean>(true)
    val showZeroCaseLiveData: LiveData<Boolean>
        get() = _showZeroCaseLiveData

    fun searchNews(searchTerm: String?) {
        _progressBarVisibilityLiveData.value = true

        viewModelScope.launch {

            val body = SearchRequestBody(keyword = searchTerm ?: "")
            val response = repo.searchNews(body)

            if (response.status == Status.SUCCESS) {
                parseArticles(response.data)
            } else if (response.status == Status.ERROR) {
                Timber.e("Response = ${response.message}")
            }

            _progressBarVisibilityLiveData.postValue(false)
        }
    }

    private fun parseArticles(response: NewsModel?) {
        val results = response?.articles?.results

       results?.let { searchResults ->
           Timber.e("parseArticles")
           if (searchResults.isNotEmpty()) {
               _showZeroCaseLiveData.postValue(false)
               _searchLiveData.postValue(searchResults)
           } else {
               _showZeroCaseLiveData.postValue(true)
               _searchLiveData.postValue(null)
           }
           return
       }

        Timber.e("parseArticles 2")
        _showZeroCaseLiveData.postValue(true)
        _searchLiveData.postValue(null)
        // todo: show error message
    }

}