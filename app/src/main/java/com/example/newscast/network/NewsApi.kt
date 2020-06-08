package com.example.newscast.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * App's NewsApi, powered by "http://eventregistry.org/"
 */
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
                    .client(OkHttpClient.Builder())
                    .build()
                retrofit as Retrofit
            }
            else -> retrofit as Retrofit
        }
    }

}

private fun Retrofit.Builder.client(builder: OkHttpClient.Builder): Retrofit.Builder {
    builder.apply {
        connectTimeout(15, TimeUnit.SECONDS)
        readTimeout(15, TimeUnit.SECONDS)
        writeTimeout(15, TimeUnit.SECONDS)
    }
    return client(builder.build())
}
