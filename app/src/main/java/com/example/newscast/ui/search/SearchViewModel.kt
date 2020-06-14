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

    private val _errorMessageLiveData = MutableLiveData<Boolean>(false)
    val errorMessageLiveData: LiveData<Boolean>
        get() = _errorMessageLiveData

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
                _errorMessageLiveData.postValue(true)
            }

            _progressBarVisibilityLiveData.postValue(false)
        }
    }

    private fun parseArticles(response: NewsModel?) {
        val results = response?.articles?.results
        if (results != null && results.isNotEmpty()) {
            _showZeroCaseLiveData.postValue(false)
            _searchLiveData.postValue(results)
        } else {
            noResults()
        }
    }

    private fun noResults() {
        _showZeroCaseLiveData.postValue(true)
        _searchLiveData.postValue(null)
        _errorMessageLiveData.postValue(true)
    }

}