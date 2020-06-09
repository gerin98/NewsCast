package com.example.newscast.ui.newspaper

import android.graphics.Paint
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.newscast.R
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.network.model.SourceModel
import com.example.newscast.ui.browse.MainActivity
import kotlinx.android.synthetic.main.activity_news_paper.*
import timber.log.Timber

class NewsPaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_paper)

        val result: ResultsModel? = intent.extras?.get(MainActivity.NEWS_ARTICLE_INTENT_FLAGS) as? ResultsModel
        val topic: String? = intent.getStringExtra(MainActivity.NEWS_TOPIC_INTENT_FLAGS)
        var source: SourceModel? = null

        result?.let {
            Timber.d("Article to be displayed: ${it.title}")

            news_paper_article_title.text = it.title
            news_paper_article_text.text = it.body

            source = it.source
        }

        source?.let {
            news_paper_article_author.text = authorTextHelper(it)
        }

        topic?.let {
            news_paper_article_tag.text = String.format(getString(R.string.news_paper_news_topic), it)
        }

    }

    // Todo: move to helper later on
    private fun authorTextHelper(source: SourceModel): SpannableString {
        val authorText =  String.format(getString(R.string.news_paper_author), source.title)
        val underlinedContent = SpannableString(authorText)
        underlinedContent.setSpan(UnderlineSpan(), 3, underlinedContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return underlinedContent
    }

}