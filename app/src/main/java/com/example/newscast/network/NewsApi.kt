package com.example.newscast.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsApi {

    companion object {
        private var retrofit: Retrofit? = null
        private val BASE_URL = "http://eventregistry.org/api/v1/article/"

        fun getRetrofitInstance(): Retrofit = when (retrofit) {
            null -> {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(
                        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    ))
                    .build()
                retrofit as Retrofit
            }
            else -> retrofit as Retrofit
        }
    }

}