package com.example.newscast.network

import okhttp3.Interceptor
import okhttp3.Response

// Todo: fix api keys here
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
//        val url = req.url().newBuilder().addQueryParameter("APPID", "your_key").build()
//        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}