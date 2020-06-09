package com.example.newscast.application

import android.app.Application
import com.example.newscast.BuildConfig
import com.example.newscast.network.networkModule
import com.example.newscast.ui.browse.newsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class NewsCastApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@NewsCastApplication)
            modules(listOf(networkModule, newsModule))
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}
