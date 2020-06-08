package com.example.newscast.ui.browse

import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.databinding.ActivityMainBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
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
                Toast.makeText(this, "World clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_us -> {
                Toast.makeText(this, "US clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_politics -> {
                Toast.makeText(this, "Politics clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_business -> {
                Toast.makeText(this, "Business clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_tech -> {
                Toast.makeText(this, "Tech clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_science -> {
                Toast.makeText(this, "Science clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_sports -> {
                Toast.makeText(this, "Sports clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_travel -> {
                Toast.makeText(this, "Travel clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.menu_culture -> {
                Toast.makeText(this, "Culture clicked", Toast.LENGTH_SHORT).show()
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