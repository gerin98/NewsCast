package com.example.newscast.application

import android.app.Application
import com.example.newscast.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsCastApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsCastApplication)
            modules(listOf(networkModule))
        }
    }

}