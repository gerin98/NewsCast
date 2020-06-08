package com.example.newscast.ui.browse

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.ui.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
            drawerLayout.openDrawer(Gravity.LEFT)

        }

        initLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bottom_app_bar, menu)
        return true
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