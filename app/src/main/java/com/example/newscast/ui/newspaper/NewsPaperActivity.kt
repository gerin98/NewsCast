package com.example.newscast.ui.newspaper

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.example.newscast.R
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.network.model.SourceModel
import com.example.newscast.ui.browse.BrowseActivity
import com.example.newscast.utils.glide.GlideApp
import kotlinx.android.synthetic.main.activity_news_paper.*
import timber.log.Timber


class NewsPaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_paper)

        val result: ResultsModel? = intent.extras?.get(BrowseActivity.NEWS_ARTICLE_INTENT_FLAGS) as? ResultsModel
        val topic: String? = intent.getStringExtra(BrowseActivity.NEWS_TOPIC_INTENT_FLAGS)
        var source: SourceModel? = null
        var imageUrl: String? = null

        result?.let {
            Timber.d("Article to be displayed: ${it.title}")

            news_paper_article_title.text = it.title
            news_paper_article_text.text = it.body

            if (it.url?.isNotEmpty() == true) {
                news_paper_article_url.text = String.format(getString(R.string.news_paper_news_url), it.url)
            }

            imageUrl = it.image
            source = it.source
        }

        source?.let {
            news_paper_article_author.text = authorTextHelper(it)
        }

        topic?.let {
            news_paper_article_tag.visibility = View.VISIBLE
            news_paper_article_tag.text = String.format(getString(R.string.news_paper_news_topic), it)
        }

        if (imageUrl != null) {
            GlideApp.with(this)
                .load(imageUrl)
                .into(news_paper_article_image)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpTo(this, intent)
                return true
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Todo: move to helper later on
    private fun authorTextHelper(source: SourceModel): SpannableString {
        val authorText =  String.format(getString(R.string.news_paper_author), source.title)
        val underlinedContent = SpannableString(authorText)
        underlinedContent.setSpan(UnderlineSpan(), 3, underlinedContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return underlinedContent
    }

}