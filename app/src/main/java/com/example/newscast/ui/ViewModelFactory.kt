package com.example.newscast.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newscast.ui.browse.BrowseViewModel
import com.example.newscast.ui.search.SearchViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            BrowseViewModel::class.java -> {
                BrowseViewModel() as T
            }
            SearchViewModel::class.java -> {
                SearchViewModel() as T
            }
            else -> throw IllegalArgumentException("View Model not found")
        }
    }
}