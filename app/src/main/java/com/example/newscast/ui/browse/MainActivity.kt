package com.example.newscast.ui.browse

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.databinding.ActivityMainBinding
import com.example.newscast.network.model.NewsModel
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.ui.adapter.NewsAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    Toolbar.OnMenuItemClickListener,
    View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: ArrayList<ResultsModel?>

    private val viewModel by lazy {
        ViewModelProvider(this, MainActivityViewModelFactory()).get(MainActivityViewModel::class.java)
    }

    // Observers
    private val newsLiveDataObserver = Observer<NewsModel> { news ->
        myDataset.clear()
        val results = news.articles?.results
        results?.let{
            for (result in results) {
                myDataset.add(result)
            }
        }

        viewAdapter.notifyDataSetChanged()
    }

    private val errorMessageLiveDataObserver = Observer<Boolean> { error ->
        if (error) {
            Toast.makeText(this, "Sorry something went wrong. Please try again later.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
        this.setSupportActionBar(newsBottomAppBar)

        myDataset = ArrayList()

        viewManager = LinearLayoutManager(this)
        viewAdapter = NewsAdapter(myDataset)

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayout.VERTICAL).apply {
            this@MainActivity.getDrawable(R.drawable.divider)?.let {
                setDrawable(it)
            }
        }

        recyclerView = findViewById<RecyclerView>(R.id.newsRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(dividerItemDecoration)
        }

        navigationView.setNavigationItemSelectedListener(this)
        news_button.setOnClickListener(this)
        newsBottomAppBar.setOnMenuItemClickListener(this)
        newsBottomAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        initLiveData()
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
                // do something
                true
            }
            R.id.menu_search -> {
                // do something
                true
            }
            R.id.menu_refresh -> {
                // do something
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
            else -> {}
        }
    }

    private fun initLiveData() {
        viewModel.getInitialNews()

        viewModel.newsLiveData.observe(this, newsLiveDataObserver)
        viewModel.errorMessageLiveData.observe(this, errorMessageLiveDataObserver)
    }

}