package com.example.newscast.di

import android.app.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module

val resourceModule = module {
    single { ResourceHelper(get()) }
}

class ResourceHelper(val app: Application) {

    fun getString(resId : Int) : String {
        return app.getString(resId)
    }

    fun getString(resId : Int, vararg args: Any) : String {
        return app.getString(resId, *args)
    }

}
