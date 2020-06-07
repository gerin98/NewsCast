package com.example.newscast.utils

import com.bumptech.glide.request.RequestOptions

const val MINI_THUMB_SIZE: Int = 500

fun <T> GlideRequest<T>.miniThumbnail() : GlideRequest<T> {
    val options = RequestOptions()
        .fitCenter()
        .override(MINI_THUMB_SIZE)
    return apply(options)
}