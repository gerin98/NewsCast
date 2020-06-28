package com.example.newscast

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.example.newscast.data.room.databaseModule
import com.example.newscast.di.preferenceModule
import com.example.newscast.di.resourceModule
import com.example.newscast.network.networkModule
import com.example.newscast.repository.favouritesModule
import com.example.newscast.repository.newsModule
import com.example.newscast.utils.string.stringHelperModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class KoinTest: AutoCloseKoinTest() {

    @Before
    fun setUp() {
        val app: Context = ApplicationProvider.getApplicationContext()
        startKoin {
            androidContext(app)
            modules(listOf(
                networkModule,
                newsModule,
                favouritesModule,
                stringHelperModule,
                databaseModule,
                preferenceModule,
                resourceModule
            ))
        }
    }

    @After
    fun tearDown() {}

    @Test
    fun `Test Koin Setup`() {
        getKoin().checkModules()
    }

}
