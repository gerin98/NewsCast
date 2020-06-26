package com.example.newscast.ui.newspaper

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.newscast.R
import com.example.newscast.data.room.Articles
import com.example.newscast.databinding.ActivityNewsPaperBinding
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.ui.ViewModelFactory
import com.example.newscast.ui.browse.BrowseActivity
import com.example.newscast.utils.glide.loadImageFromUrl
import kotlinx.android.synthetic.main.activity_news_paper.*
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import timber.log.Timber

class NewsPaperActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: NewsPaperViewModel by viewModels { ViewModelFactory() }

    // Koin Components
    private val sharedPreferences: SharedPreferences by inject(named("preference_manager"))

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

    // glide callback for shared element transitions
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
            animateContentUp()
            startPostponedTransition(news_paper_article_image)
            return false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityNewsPaperBinding>(this, R.layout.activity_news_paper)
        binding.newsPaper = viewModel.newsPaperObservable
        binding.viewModel = viewModel
        postponeEnterTransition()
        initLiveData()
        setTextTheme()
        setBackgroundTheme()

        val result: ResultsModel? = intent.extras?.get(BrowseActivity.NEWS_ARTICLE_INTENT_FLAGS) as? ResultsModel
        val favouriteUri = intent.getStringExtra(BrowseActivity.FAVOURITE_NEWS_ARTICLE_INTENT_FLAGS)
        val imageTransitionName = intent.getStringExtra(BrowseActivity.TRANSITION_INTENT_FLAGS)
        val topic = intent.getStringExtra(BrowseActivity.NEWS_TOPIC_INTENT_FLAGS)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_news_paper_activity, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
            R.id.menu_share_news -> {
                shareNewsArticle()
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.news_paper_favourite_button -> {
                viewModel.favouritesButtonClick()
            }
            else -> {}
        }
    }

    private fun initLiveData() {
        viewModel.favouriteLiveData.observe(this, favouritesLiveDataObserver)
        viewModel.articleLiveData.observe(this, articleLiveDataObserver)
    }

    // load news article from network request
    private fun loadNewsArticle(result: ResultsModel?, topic: String?) {
        viewModel.prepareNewsPaper(result, topic)
        viewModel.checkIfFavourited()

        val imageUrl = result?.image
        if (imageUrl != null) {
            news_paper_article_image.loadImageFromUrl(this, imageUrl, glideListener)
        } else {
            animateContentUp()
            startPostponedEnterTransition()
        }
    }

    // request news article to be shown from db
    private fun loadNewsArticleFromDb(uri: String?) {
        viewModel.getArticleByUri(uri)
    }

    // display news article from db
    private fun showNewsArticleFromDb(article: Articles?) {
        viewModel.checkIfFavourited()

        val imageUrl = article?.imageUrl
        if (imageUrl != null) {
            news_paper_article_image.loadImageFromUrl(this, imageUrl, glideListener)
        } else {
            animateContentUp()
            startPostponedEnterTransition()
        }
    }

    // postponed translations for shared element transitions
    fun startPostponedTransition(sharedElement: View) {
        sharedElement.viewTreeObserver?.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

    // share the current news article
    private fun shareNewsArticle() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, viewModel.articleUrl)
            putExtra(Intent.EXTRA_TITLE, viewModel.articleTitle)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    // set text theme from preferences
    private fun setTextTheme() {
        val stringArray = resources.getStringArray(R.array.text_size_values)
        when(sharedPreferences.getString("textTheme", "Normal")) {
            stringArray[0] -> {
                // Comfortable
                news_paper_article_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.Headline1Comfortable))
                news_paper_article_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.Content1Comfortable))
                news_paper_article_author.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.Headline3Comfortable))
            }
            stringArray[1] -> {
                // Normal
                news_paper_article_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.Headline1Normal))
                news_paper_article_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.Content1Normal))
                news_paper_article_author.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.Headline3Normal))
            }
            stringArray[2] -> {
                // Compact
                news_paper_article_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.Headline1Compact))
                news_paper_article_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.Content1Compact))
                news_paper_article_author.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.Headline3Compact))
            }
        }
    }

    // set background theme from preferences
    private fun setBackgroundTheme() {
        val stringArray = resources.getStringArray(R.array.background_colour_values)
        when(sharedPreferences.getString("backgroundTheme", "Light")) {
            stringArray[0] -> {
                // Light
                val color = resources.getColor(R.color.light, null)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(color)) ?: Timber.e("action bar is null")
                news_paper_parent_layout.setBackgroundColor(color)
            }
            stringArray[1] -> {
                // Sepia
                val color = resources.getColor(R.color.sepia, null)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(color)) ?: Timber.e("action bar is null")
                news_paper_parent_layout.setBackgroundColor(color)
            }
            stringArray[2] -> {
                // Dark
                val color = resources.getColor(R.color.dark, null)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(color)) ?: Timber.e("action bar is null")
                news_paper_parent_layout.setBackgroundColor(color)
            }
        }
    }

    private fun animateContentUp() {
        val slideUpAnimation = AnimationUtils.loadAnimation(this@NewsPaperActivity, R.anim.slide_up)
        news_paper_text_layout.startAnimation(slideUpAnimation)
    }

}