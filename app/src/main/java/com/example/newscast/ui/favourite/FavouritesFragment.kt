package com.example.newscast.ui.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.data.room.Articles
import com.example.newscast.ui.ViewModelFactory
import com.example.newscast.ui.adapter.FavouritesAdapter
import com.example.newscast.ui.search.SearchViewModel

class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by activityViewModels { ViewModelFactory() }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataset: ArrayList<Articles?>

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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        initLiveData()
    }

    private fun setupRecyclerView(view: View) {
        dataset = ArrayList()

        viewManager = LinearLayoutManager(activity)
        viewAdapter = FavouritesAdapter(dataset)

        val dividerItemDecoration = DividerItemDecoration(activity, LinearLayout.VERTICAL).apply {
            activity?.getDrawable(R.drawable.divider)?.let {
                setDrawable(it)
            }
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.favourites_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun initLiveData() {
        viewModel.favouritesLiveData.observe(viewLifecycleOwner, favouritesLiveDataObserver)
        viewModel.getAllFavourites()
    }

}