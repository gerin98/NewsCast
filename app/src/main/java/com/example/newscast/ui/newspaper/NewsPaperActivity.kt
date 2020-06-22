package com.example.newscast.ui.newspaper

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.newscast.R
import com.example.newscast.data.room.Articles
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.network.model.SourceModel
import com.example.newscast.ui.ViewModelFactory
import com.example.newscast.ui.browse.BrowseActivity
import com.example.newscast.utils.glide.loadImageFromUrl
import com.example.newscast.utils.string.StringHelper
import kotlinx.android.synthetic.main.activity_news_paper.*
import org.koin.android.ext.android.inject
import timber.log.Timber


class NewsPaperActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: NewsPaperViewModel by viewModels { ViewModelFactory() }

    // Koin Components
    private val stringHelper by inject<StringHelper> ()

    // Observers
    private val favouritesLiveDataObserver = Observer<Boolean?> {
        if (it == true) {
            news_paper_favourite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_filled, null))
        } else if (it == false) {
            news_paper_favourite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border, null))
        }
    }

    private val articleLiveDataObserver = Observer<List<Articles?>> {
        if (it.isNotEmpty()) {
            showNewsArticleFromDb(it[0])
        }
    }

    var source: SourceModel? = null
    var title: String? = null
    var body: String? = null
    var url: String? = null
    var author: String? = null
    var imageUrl: String? = null
    var topic: String? = null
    var uri: String? = null

    private val glideListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            Timber.e("onLoadFailed")
            startPostponedEnterTransition()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            Timber.e("onResourceReady")
            startPostponedTransition(news_paper_article_image)
            return false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_paper)
        postponeEnterTransition()
        initLiveData()

        val result: ResultsModel? = intent.extras?.get(BrowseActivity.NEWS_ARTICLE_INTENT_FLAGS) as? ResultsModel
        val favouriteUri = intent.getStringExtra(BrowseActivity.FAVOURITE_NEWS_ARTICLE_INTENT_FLAGS)
        val imageTransitionName = intent.getStringExtra(BrowseActivity.TRANSITION_INTENT_FLAGS)
        topic = intent.getStringExtra(BrowseActivity.NEWS_TOPIC_INTENT_FLAGS)

        if (imageTransitionName != null) {
            news_paper_article_image.transitionName = imageTransitionName
        }

        if (result != null) {
            loadNewsArticle(result, topic)
        } else {
            loadNewsArticleFromDb(favouriteUri)
        }

        news_paper_favourite_button.setOnClickListener(this)
    }

    private fun loadNewsArticle(result: ResultsModel?, topic: String?) {
        result?.let {
            Timber.d("Article to be displayed: ${it.title}")
            title = it.title
            body = it.body
            uri = it.uri
            imageUrl = it.image
            source = it.source

            news_paper_article_title.text = title
            news_paper_article_text.text = body

            if (it.url?.isNotEmpty() == true) {
                url = it.url
                news_paper_article_url.text = String.format(getString(R.string.news_paper_news_url), url)
            }
        }

        source?.let {
            author = it.title
            val authorText =  String.format(getString(R.string.news_paper_author), author)
            news_paper_article_author.text = stringHelper.underlineText(authorText, 3)
        }

        topic?.let {
            news_paper_article_tag.visibility = View.VISIBLE
            news_paper_article_tag.text = String.format(getString(R.string.news_paper_news_topic), it)
        }

        imageUrl?.let{
            news_paper_article_image.loadImageFromUrl(this, it, glideListener)
        }

        if (imageUrl == null) {
            startPostponedEnterTransition()
        }

        viewModel.checkIfFavourited(uri)
    }

    private fun loadNewsArticleFromDb(uri: String?) {
        viewModel.getArticleByUri(uri)
    }

    private fun showNewsArticleFromDb(article: Articles?) {
        if (article != null) {
            title = article.title
            body = article.body
            url = article.url
            author = article.author
            imageUrl = article.imageUrl
            uri = article.uri
        }

        news_paper_article_title.text = title
        news_paper_article_text.text = body
        news_paper_article_url.text = String.format(getString(R.string.news_paper_news_url), url)
        val authorText =  String.format(getString(R.string.news_paper_author), author)
        news_paper_article_author.text = stringHelper.underlineText(authorText, 3)

        topic?.let {
            news_paper_article_tag.visibility = View.VISIBLE
            news_paper_article_tag.text = String.format(getString(R.string.news_paper_news_topic), it)
        }

        imageUrl?.let{
            news_paper_article_image.loadImageFromUrl(this, it, glideListener)
        }

        if (imageUrl == null) {
            startPostponedEnterTransition()
        }

        viewModel.checkIfFavourited(uri)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpTo(this, intent)
                return true
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.news_paper_favourite_button -> {
                viewModel.favouritesButtonClick(uri, title, body, url, imageUrl, author, topic)
            }
            else -> {}
        }
    }

    private fun initLiveData() {
        viewModel.favouriteLiveData.observe(this, favouritesLiveDataObserver)
        viewModel.articleLiveData.observe(this, articleLiveDataObserver)
    }

    fun startPostponedTransition(sharedElement: View) {
        sharedElement.viewTreeObserver?.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

}