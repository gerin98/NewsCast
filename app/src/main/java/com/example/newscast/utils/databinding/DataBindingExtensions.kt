package com.example.newscast.utils.databinding

import android.view.View
import androidx.databinding.BindingAdapter

object DataBindingExtensions {

    /**
     * DataBinding adapter to map a boolean live data to a view's visibility
     */
    @BindingAdapter("android:booleanVisibility")
    @JvmStatic
    fun setVisibility(targetView: View, data: Boolean?) {
        targetView.visibility = when (data) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

}