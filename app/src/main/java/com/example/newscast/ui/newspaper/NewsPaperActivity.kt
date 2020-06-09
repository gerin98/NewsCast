package com.example.newscast.ui.newspaper

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.newscast.R
import com.example.newscast.network.model.ResultsModel
import timber.log.Timber

class NewsPaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_paper)
        val result: ResultsModel? = intent.extras?.get("news_article") as? ResultsModel
        result?.let {
            Timber.d("Article to be displayed: ${it.title}")
        }
    }
}