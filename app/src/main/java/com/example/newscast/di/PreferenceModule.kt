package com.example.newscast.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val preferenceModule = module {
    single(named("default_preferences")) { getSharedPrefs(androidApplication()) }
    single(named("preference_manager")) { getPreferenceManager(androidApplication()) }
}

fun getSharedPrefs(app: Application): SharedPreferences {
    return app.getSharedPreferences("default", Context.MODE_PRIVATE)
}

fun getPreferenceManager(app: Application): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(app)
}