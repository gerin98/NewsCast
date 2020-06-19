package com.example.newscast.ui.newspaper

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.example.newscast.R
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.network.model.SourceModel
import com.example.newscast.ui.browse.BrowseActivity
import com.example.newscast.utils.glide.GlideApp
import com.example.newscast.utils.string.StringHelper
import kotlinx.android.synthetic.main.activity_news_paper.*
import org.koin.android.ext.android.inject
import timber.log.Timber


class NewsPaperActivity : AppCompatActivity() {

    private val stringHelper by inject<StringHelper> ()

    private val viewModel: NewsPaperViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_paper)

        val result: ResultsModel? = intent.extras?.get(BrowseActivity.NEWS_ARTICLE_INTENT_FLAGS) as? ResultsModel
        val topic: String? = intent.getStringExtra(BrowseActivity.NEWS_TOPIC_INTENT_FLAGS)
        var source: SourceModel? = null

        var title: String? = null
        var body: String? = null
        var url: String? = null
        var author: String? = null
        var imageUrl: String? = null

        result?.let {
            Timber.d("Article to be displayed: ${it.title}")
            title = it.title
            body = it.body

            news_paper_article_title.text = title
            news_paper_article_text.text = body

            if (it.url?.isNotEmpty() == true) {
                url = it.url
                news_paper_article_url.text = String.format(getString(R.string.news_paper_news_url), it.url)
            }

            imageUrl = it.image
            source = it.source
        }

        source?.let {
            author = it.title
            val authorText =  String.format(getString(R.string.news_paper_author), it.title)
            news_paper_article_author.text = stringHelper.underlineText(authorText, 3)
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

}