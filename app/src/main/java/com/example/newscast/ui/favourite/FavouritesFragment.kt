package com.example.newscast.ui.favourite

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.data.room.Articles
import com.example.newscast.databinding.FragmentFavouritesBinding
import com.example.newscast.ui.ViewModelFactory
import com.example.newscast.ui.adapter.FavouritesAdapter
import com.example.newscast.ui.adapter.RecyclerViewItemTouchListener
import com.example.newscast.ui.browse.BrowseActivity
import com.example.newscast.ui.newspaper.NewsPaperActivity
import timber.log.Timber

class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by activityViewModels { ViewModelFactory() }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataset: ArrayList<Articles?>

    private val gestureDetector = RecyclerViewItemTouchListener(activity, object: RecyclerViewItemTouchListener.OnItemClickEventListener {
        override fun onItemClick(clickedView: View?, adapterPosition: Int) {
            Timber.d("onItemClick")
            recyclerViewOnClick(dataset[adapterPosition], clickedView)
        }

        override fun onItemLongClick(longClickedView: View?, adapterPosition: Int) {
            Timber.d("onItemLongClick")
        }

        override fun onItemDoubleClick(doubleClickedView: View?, adapterPosition: Int) {
            Timber.d("onItemDoubleClick")
        }

    })

    // Observers
    private val favouritesLiveDataObserver = Observer<List<Articles>?> {
        dataset.clear()

        if (it != null && it.isNotEmpty()) {
            for (result in it) {
                dataset.add(result)
            }
            viewModel.foundResults()
        } else {
            viewModel.foundNoResults()
        }

        viewAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFavouritesBinding = DataBindingUtil.inflate<FragmentFavouritesBinding>(inflater, R.layout.fragment_favourites, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@FavouritesFragment.viewModel
        }

        return binding.root
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
            addOnItemTouchListener(gestureDetector)
        }
    }

    private fun recyclerViewOnClick(item: Articles?, clickedView: View?) {
        val image = clickedView?.findViewById<ImageView>(R.id.news_tile_image)
        val transitionName =
            if (image != null) {
                ViewCompat.getTransitionName(image)
            } else {
                null
            }

        val intent = Intent(activity, NewsPaperActivity::class.java)
        var options: ActivityOptions? = null
        if (transitionName != null) {
            intent.putExtra(BrowseActivity.TRANSITION_INTENT_FLAGS, transitionName)
            options = ActivityOptions.makeSceneTransitionAnimation(activity, image, transitionName)
        }
        intent.putExtra(BrowseActivity.FAVOURITE_NEWS_ARTICLE_INTENT_FLAGS, item?.uri)
        intent.putExtra(BrowseActivity.NEWS_TOPIC_INTENT_FLAGS, item?.topic)
        startActivity(intent, options?.toBundle())
    }

    private fun initLiveData() {
        viewModel.favouritesLiveData.observe(viewLifecycleOwner, favouritesLiveDataObserver)
    }

}