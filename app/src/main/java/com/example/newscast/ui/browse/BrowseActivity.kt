package com.example.newscast.ui.browse

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.newscast.R
import com.example.newscast.ui.ViewModelFactory
import com.example.newscast.ui.favourite.FavouritesActivity
import com.example.newscast.ui.search.SearchActivity
import com.example.newscast.ui.settings.SettingsActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_browse.*

class BrowseActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    Toolbar.OnMenuItemClickListener,
    View.OnClickListener {

    companion object {
        const val NEWS_ARTICLE_INTENT_FLAGS = "NEWS_ARTICLE_INTENT_FLAGS"
        const val NEWS_TOPIC_INTENT_FLAGS = "NEWS_TOPIC_INTENT_FLAGS"
        const val FAVOURITE_NEWS_ARTICLE_INTENT_FLAGS = "FAVOURITE_NEWS_ARTICLE_INTENT_FLAGS"
        const val FAVOURITE_NEWS_TOPIC_INTENT_FLAGS = "FAVOURITE_NEWS_TOPIC_INTENT_FLAGS"
        const val TRANSITION_INTENT_FLAGS = "TRANSITION_INTENT_FLAGS"
    }

    private val viewModel: BrowseViewModel by viewModels { ViewModelFactory() }

    // Observers
    private val errorMessageLiveDataObserver = Observer<Boolean> { error ->
        if (error) {
            val toast = Toast.makeText(this, "Sorry something went wrong. Please try again later.", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.BOTTOM, 0, 250)
            toast.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        this.setSupportActionBar(newsBottomAppBar)

        navigationView.setNavigationItemSelectedListener(this)
        news_button.setOnClickListener(this)
        newsBottomAppBar.setOnMenuItemClickListener(this)
        newsBottomAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        settings_button.setOnClickListener(this)

        initLiveData()

        val fragment = BrowseFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.browse_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bottom_app_bar, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_world -> {
                viewModel.getNewsForTopic(SearchLinks.World())
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_us -> {
                viewModel.getNewsForTopic(SearchLinks.Us())
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_politics -> {
                viewModel.getNewsForTopic(SearchLinks.Politics())
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_business -> {
                viewModel.getNewsForTopic(SearchLinks.Business())
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_tech -> {
                viewModel.getNewsForTopic(SearchLinks.Tech())
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_science -> {
                viewModel.getNewsForTopic(SearchLinks.Science())
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_sports -> {
                viewModel.getNewsForTopic(SearchLinks.Sports())
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_travel -> {
                viewModel.getNewsForTopic(SearchLinks.Travel())
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_culture -> {
                viewModel.getNewsForTopic(SearchLinks.Culture())
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.menu_favourites -> {
                val intent = Intent(this, FavouritesActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_search -> {
                val options = ActivityOptions.makeSceneTransitionAnimation(this)
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent, options.toBundle())
                true
            }
            R.id.menu_refresh -> {
                viewModel.refreshNews()
                true
            }
            else -> false
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.news_button -> {
                viewModel.getLatestNews()
            }
            R.id.settings_button -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            else -> {}
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initLiveData() {
        viewModel.getInitialNews()
        viewModel.errorMessageLiveData.observe(this, errorMessageLiveDataObserver)
    }

}