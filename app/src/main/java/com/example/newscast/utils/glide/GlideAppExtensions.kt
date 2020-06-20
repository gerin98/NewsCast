package com.example.newscast.utils.glide

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_news_paper.*

const val MINI_THUMB_SIZE: Int = 500

fun <T> GlideRequest<T>.miniThumbnail() : GlideRequest<T> {
    val options = RequestOptions()
        .fitCenter()
        .override(MINI_THUMB_SIZE)
    return apply(options)
}

fun ImageView.loadImageFromUrl(activity: Activity, imageUrl: String) {
    GlideApp.with(activity)
        .load(imageUrl)
        .into(this)
}