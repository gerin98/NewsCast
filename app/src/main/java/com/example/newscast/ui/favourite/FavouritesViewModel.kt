package com.example.newscast.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newscast.data.room.Articles
import com.example.newscast.repository.FavouritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class FavouritesViewModel : ViewModel(), KoinComponent {

    /** Koin Components **/
    private val repo by inject<FavouritesRepository>()

    /** Favourites LiveData **/
    private var _favouritesLiveData = repo.getAllFavouritesLiveData()
    val favouritesLiveData: LiveData<List<Articles>?>
        get() = _favouritesLiveData

    private val _showZeroCaseLiveData = MutableLiveData<Boolean>(true)
    val showZeroCaseLiveData: LiveData<Boolean>
        get() = _showZeroCaseLiveData

    fun clearAllFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.clearFavourites()
            foundNoResults()
        }
    }

    fun deleteSelectedFavourites(selectionItems: Array<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteArticleByUris(selectionItems)
        }
    }

    fun foundNoResults() {
        _showZeroCaseLiveData.postValue(true)
    }

    fun foundResults() {
        _showZeroCaseLiveData.postValue(false)
    }

}