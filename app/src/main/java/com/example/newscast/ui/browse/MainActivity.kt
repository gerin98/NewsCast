package com.example.newscast.ui.browse

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.ui.adapter.NewsAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: ArrayList<ResultsModel?>

    private val viewModel by lazy {
        ViewModelProvider(this, MainActivityViewModelFactory()).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setSupportActionBar(newsBottomAppBar)

        myDataset = ArrayList()

        viewManager = LinearLayoutManager(this)
        viewAdapter = NewsAdapter(myDataset)

        recyclerView = findViewById<RecyclerView>(R.id.newsRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayout.VERTICAL).apply {
            this@MainActivity.getDrawable(R.drawable.divider)?.let {
                setDrawable(it)
            }
        }
        recyclerView.addItemDecoration(dividerItemDecoration)

        news_button.setOnClickListener{
            viewModel.getLatestNews()
        }

        newsBottomAppBar.setOnMenuItemClickListener {item ->
            when(item.itemId) {
                R.id.menu_favourites -> {
                    // do something
                    true
                }
                R.id.menu_search -> {
                    // do something
                    true
                }
                else -> false
            }

        }

        newsBottomAppBar.setNavigationOnClickListener {item ->
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener(this)

        initLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bottom_app_bar, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//         Handle item selection
        return when (item.itemId) {
            R.id.menu_settings -> {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initLiveData() {
        viewModel.getNews()

        viewModel.newsLiveData.observe(this, Observer { news ->
            Log.e("Gerin", "Title: ${news.articles?.results?.get(5)?.title}")

            myDataset.clear()
            val results = news.articles?.results
            results?.let{
                for (result in results) {
                    myDataset.add(result)
                }
            }

            viewAdapter.notifyDataSetChanged()
        })
    }

}