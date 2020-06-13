package com.example.newscast.ui.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivityViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            MainActivityViewModel::class.java -> {
                MainActivityViewModel() as T
            }
            else -> throw IllegalArgumentException("View Model not found")
        }
    }
}