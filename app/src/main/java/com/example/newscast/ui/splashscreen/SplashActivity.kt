package com.example.newscast.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newscast.R
import com.example.newscast.ui.browse.BrowseActivity
import kotlinx.coroutines.*
import okhttp3.internal.wait

class SplashActivity : AppCompatActivity() {

    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityScope.launch {
            delay(2000L)

            val newIntent = Intent(this@SplashActivity, BrowseActivity::class.java)
            startActivity(newIntent)
        }

    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

}