package com.example.newscast.ui.newspaper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newscast.data.room.NewsDatabase
import com.example.newscast.repository.FavouritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class NewsPaperViewModel: ViewModel(), KoinComponent {

    private val favouritesRepository by inject<FavouritesRepository>()


    fun favourite() {
        viewModelScope.launch {
            favouritesRepository.getAllFavourites()
        }
    }

    fun insert(title: String? = null,
               body: String? = null,
               url: String? = null,
               imageUrl: String? = null,
               author: String? = null,
               topic: String? = null) {
        Timber.e("inserting into db")
        viewModelScope.launch(Dispatchers.IO) {
            favouritesRepository.insertArticle(title, body, url, imageUrl, author, topic)
        }
    }


}