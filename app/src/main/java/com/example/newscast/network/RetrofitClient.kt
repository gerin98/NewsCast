package com.example.newscast.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { provideLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideConverterFactory() }
    single(named("event_registry")) { provideRetrofit(get(), get(), BASE_URL) }
    factory { NetworkResponseHelper() }
    factory { provideNewsApi( get(named("event_registry")) ) }
}

private const val BASE_URL = "http://eventregistry.org/api/v1/article/"

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
}

fun provideConverterFactory(): Converter.Factory {
    return MoshiConverterFactory.create(
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    )
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()
}

fun provideNewsApi(retrofit: Retrofit): NewsService = retrofit.create(NewsService::class.java)