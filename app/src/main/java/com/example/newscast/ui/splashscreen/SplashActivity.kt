package com.example.newscast.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newscast.ui.browse.BrowseActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newIntent = Intent(this@SplashActivity, BrowseActivity::class.java)
        startActivity(newIntent)
    }

}