package com.example.newscast.utils.glide

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

const val MINI_THUMB_SIZE: Int = 500

fun <T> GlideRequest<T>.miniThumbnail() : GlideRequest<T> {
    val options = RequestOptions()
        .fitCenter()
        .override(MINI_THUMB_SIZE)
    return apply(options)
}

fun ImageView.loadImageFromUrl(activity: Activity, imageUrl: String, listener: RequestListener<Drawable>? = null) {
    GlideApp.with(activity)
        .load(imageUrl)
        .listener(listener)
        .into(this)
}

fun ImageView.loadImageFromUrl(context: Context, imageUrl: String, listener: RequestListener<Drawable>? = null) {
    GlideApp.with(context)
        .load(imageUrl)
        .listener(listener)
        .into(this)
}

fun ImageView.loadThumbnailFromUrl(context: Context, imageUrl: String, listener: RequestListener<Drawable>? = null) {
    GlideApp.with(context)
        .load(imageUrl)
        .miniThumbnail()
        .listener(listener)
        .into(this)
}