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
    private val _favouritesLiveData = MutableLiveData<List<Articles>?>()
    val favouritesLiveData: LiveData<List<Articles>?>
        get() = _favouritesLiveData

    fun getAllFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            val favouriteArticles = repo.getAllFavourites()
            _favouritesLiveData.postValue(favouriteArticles)
        }
    }

}