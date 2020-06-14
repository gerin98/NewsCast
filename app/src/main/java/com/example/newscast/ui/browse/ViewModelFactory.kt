package com.example.newscast.ui.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newscast.ui.search.SearchViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            MainActivityViewModel::class.java -> {
                MainActivityViewModel() as T
            }
            SearchViewModel::class.java -> {
                SearchViewModel() as T
            }
            else -> throw IllegalArgumentException("View Model not found")
        }
    }
}