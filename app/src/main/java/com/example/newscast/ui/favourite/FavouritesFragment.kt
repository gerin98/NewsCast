package com.example.newscast.ui.favourite

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.data.room.Articles
import com.example.newscast.databinding.FragmentFavouritesBinding
import com.example.newscast.di.ResourceHelper
import com.example.newscast.ui.ViewModelFactory
import com.example.newscast.ui.adapter.FavouritesAdapter
import com.example.newscast.ui.adapter.RecyclerViewLookup
import com.example.newscast.ui.adapter.RecyclerViewTouchListener
import com.example.newscast.ui.browse.BrowseActivity
import com.example.newscast.ui.newspaper.NewsPaperActivity
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class FavouritesFragment : Fragment(), KoinComponent {

    private val viewModel: FavouritesViewModel by activityViewModels { ViewModelFactory() }

    // Koin components
    private val resourceHelper by inject<ResourceHelper>()

    // recycler view
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: FavouritesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataset: ArrayList<Articles?>

    private val gestureDetector = RecyclerViewTouchListener(activity, object: RecyclerViewTouchListener.OnTouchEventListener {
        override fun onClick(clickedView: View?, adapterPosition: Int) {
            Timber.d("onClick")
            if (!selectionModeOn && !unselectedAllItems) {
                recyclerViewOnClick(dataset[adapterPosition], clickedView)
            }
            unselectedAllItems = false
        }

        override fun onDoubleClick(doubleClickedView: View?, adapterPosition: Int) {
            Timber.d("onDoubleClick")
        }

        override fun onLongPress(longPressedView: View?, adapterPosition: Int) {
            Timber.d("onLongPress")
            when (actionMode) {
                null -> {
                    actionMode = activity?.startActionMode(actionModeCallback)
                    view?.isSelected = true
                }
                else -> {}
            }
        }

    })

    // selection mode
    var tracker : SelectionTracker<Long>? = null
    private var actionMode: ActionMode? = null
    private var selectionModeOn = false
    private var unselectedAllItems = false

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.menu_favourites_action_mode, menu)
            addSelectionTracker()
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_garbage -> {
                    deleteSelectedItems()
                    mode.finish()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            removeSelectionTracker()
            actionMode = null
            viewAdapter.notifyDataSetChanged()
        }
    }

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

    private val trackerObserver =  object : SelectionTracker.SelectionObserver<Long>() {
        override fun onSelectionChanged() {
            val numberOfItems: Int = tracker?.selection?.size() ?: 0
            Timber.e("onSelectionChanged, $numberOfItems")
            if (numberOfItems == 0 && actionMode != null) {
                unselectedAllItems = true
                actionMode?.finish()
            }
        }
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

    private fun initLiveData() {
        viewModel.favouritesLiveData.observe(viewLifecycleOwner, favouritesLiveDataObserver)
    }

    // Recycler view functions
    private fun setupRecyclerView(view: View) {
        dataset = ArrayList()

        val dividerItemDecoration = DividerItemDecoration(activity, LinearLayout.VERTICAL).apply {
            activity?.getDrawable(R.drawable.divider)?.let {
                setDrawable(it)
            }
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = FavouritesAdapter(dataset, resourceHelper)
        recyclerView = view.findViewById(R.id.favourites_recycler_view)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(dividerItemDecoration)
            addOnItemTouchListener(gestureDetector)
        }

        setupTracker(recyclerView)
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

    // Action mode functions
    private fun setupTracker(recyclerView: RecyclerView) {
        tracker = SelectionTracker
            .Builder("selectionId",
                recyclerView,
                StableIdKeyProvider(recyclerView),
                RecyclerViewLookup(recyclerView),
                StorageStrategy.createLongStorage())
            .withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()

        tracker?.addObserver(trackerObserver)
    }

    fun addSelectionTracker() {
        selectionModeOn = true
        viewAdapter.apply {
            setTracker(tracker)
        }
    }

    fun removeSelectionTracker() {
        selectionModeOn = false
        tracker?.clearSelection()
        viewAdapter.apply {
            selectedItems.clear()
            removeTracker()
        }
    }

    fun deleteSelectedItems() {
        val selectedItems = (recyclerView.adapter as FavouritesAdapter).selectedItems
        viewModel.deleteSelectedFavourites(selectedItems.toTypedArray())
    }

}