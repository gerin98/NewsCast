package com.example.newscast.ui.newspaper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newscast.data.room.Articles
import com.example.newscast.data.room.NewsDatabase
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.repository.FavouritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class NewsPaperViewModel: ViewModel(), KoinComponent {

    private val favouritesRepository by inject<FavouritesRepository>()

    private val _favouriteLiveData = MutableLiveData<Boolean?>(null)
    val favouriteLiveData: LiveData<Boolean?>
        get() = _favouriteLiveData

    private val _articleLiveData = MutableLiveData<List<Articles?>>()
    val articleLiveData: LiveData<List<Articles?>>
        get() = _articleLiveData

    private fun addToFavourites(uri: String? = null,
                                title: String? = null,
                                body: String? = null,
                                url: String? = null,
                                imageUrl: String? = null,
                                author: String? = null,
                                topic: String? = null) {
        Timber.e("inserting $uri into db")
        viewModelScope.launch(Dispatchers.IO) {
            favouritesRepository.insertArticle(uri, title, body, url, imageUrl, author, topic)
            _favouriteLiveData.postValue(true)
        }
    }

    private fun removeFromFavourites(uri: String? = null) {
        if (!uri.isNullOrEmpty()) {
            Timber.e("removing $uri from db")
            viewModelScope.launch(Dispatchers.IO) {
                favouritesRepository.deleteArticleByUri(uri)
                _favouriteLiveData.postValue(false)
            }
        }
    }

    fun checkIfFavourited(uri: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val status = favouritesRepository.isFavourited(uri)
            _favouriteLiveData.postValue(status)
        }
    }

    fun getArticleByUri(uri: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val articles = favouritesRepository.getArticlesByUri(uri)
            _articleLiveData.postValue(articles)
        }
    }

    fun favouritesButtonClick(uri: String? = null,
                              title: String? = null,
                              body: String? = null,
                              url: String? = null,
                              imageUrl: String? = null,
                              author: String? = null,
                              topic: String? = null) {
        when (_favouriteLiveData.value) {
            null -> {
                return
            }
            false -> {
                addToFavourites(uri, title, body, url, imageUrl, author, topic)
            }
            else -> {
                removeFromFavourites(uri)
            }
        }

    }


}