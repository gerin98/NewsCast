package com.example.newscast.ui.favourite

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.data.room.Articles
import com.example.newscast.ui.ViewModelFactory
import com.example.newscast.ui.adapter.FavouritesAdapter
import kotlinx.android.synthetic.main.activity_favourites.*

class FavouritesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataset: ArrayList<Articles?>

    private val viewModel: FavouritesViewModel by viewModels { ViewModelFactory() }

    // Observers
    private val favouritesLiveDataObserver = Observer<List<Articles?>?> {
        dataset.clear()

        if (it != null) {
            for (result in it) {
                dataset.add(result)
            }
        }

        viewAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        setupRecyclerView(favouritesRecyclerView)
        initLiveData()
    }

    private fun initLiveData() {
        viewModel.favouritesLiveData.observe(this, favouritesLiveDataObserver)
        viewModel.getAllFavourites()
    }

    private fun setupRecyclerView(view: RecyclerView) {
        dataset = ArrayList()

        viewManager = LinearLayoutManager(this)
        viewAdapter = FavouritesAdapter(dataset)

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayout.VERTICAL).apply {
            this@FavouritesActivity.getDrawable(R.drawable.divider)?.let {
                setDrawable(it)
            }
        }

        recyclerView = view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }

}