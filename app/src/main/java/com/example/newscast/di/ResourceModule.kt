package com.example.newscast.di

import android.app.Application
import android.graphics.Color
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

    fun getColor(resId: Int) :Int {
        return app.resources.getColor(resId, null)
    }

}
