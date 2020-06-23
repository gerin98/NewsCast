package com.example.newscast.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.NavUtils
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.newscast.R
import com.example.newscast.ui.ViewModelFactory
import timber.log.Timber

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModels { ViewModelFactory() }

    // Observers
    private val errorMessageLiveDataObserver = Observer<Boolean> { error ->
        if (error) {
            Toast.makeText(this, "Sorry something went wrong. Please try again later.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val fragment = SearchFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.search_fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        initLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search_activity, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_search_view).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Timber.e("Search query: $query")
                    viewModel.searchNews(query)
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })
            requestFocus()

            // remove default magnifying glass icon
            val magIcon: ImageView = findViewById(androidx.appcompat.R.id.search_mag_icon)
            magIcon.visibility = View.GONE
            magIcon.setImageDrawable(null)
        }

        return true
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initLiveData() {
        viewModel.errorMessageLiveData.observe(this, errorMessageLiveDataObserver)
    }

}