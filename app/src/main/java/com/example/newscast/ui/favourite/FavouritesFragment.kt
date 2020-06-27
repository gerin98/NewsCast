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
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.data.room.Articles
import com.example.newscast.databinding.FragmentFavouritesBinding
import com.example.newscast.ui.ViewModelFactory
import com.example.newscast.ui.adapter.FavouritesAdapter
import com.example.newscast.ui.adapter.RecyclerViewTouchListener
import com.example.newscast.ui.browse.BrowseActivity
import com.example.newscast.ui.newspaper.NewsPaperActivity
import timber.log.Timber

class FavouritesFragment : Fragment() {

    class MyLookup(private val rv: RecyclerView): ItemDetailsLookup<Long>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
            val view = rv.findChildViewUnder(event.x, event.y)
            if(view != null) {
                return (rv.getChildViewHolder(view) as FavouritesAdapter.FavouritesViewHolder)
                    .getItemDetails()
            }
            return null
        }
    }

    var tracker : SelectionTracker<Long>? = null

    private val viewModel: FavouritesViewModel by activityViewModels { ViewModelFactory() }
    private var actionMode: ActionMode? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: FavouritesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataset: ArrayList<Articles?>

    private var selectionModeOn = false

    private val gestureDetector = RecyclerViewTouchListener(activity, object: RecyclerViewTouchListener.OnTouchEventListener {
        override fun onClick(clickedView: View?, adapterPosition: Int) {
            Timber.d("onClick")
            if (!selectionModeOn) {
                recyclerViewOnClick(dataset[adapterPosition], clickedView)
            }
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

        val dividerItemDecoration = DividerItemDecoration(activity, LinearLayout.VERTICAL).apply {
            activity?.getDrawable(R.drawable.divider)?.let {
                setDrawable(it)
            }
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = FavouritesAdapter(dataset)
        recyclerView = view.findViewById(R.id.favourites_recycler_view)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(dividerItemDecoration)
            addOnItemTouchListener(gestureDetector)
        }

        tracker = SelectionTracker.Builder("selection-1",
            recyclerView,
            StableIdKeyProvider(recyclerView),
            MyLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        (recyclerView.adapter as FavouritesAdapter).apply {
            setTracker(tracker)
        }

        tracker?.addObserver(object: SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                val nItems: Int? = tracker?.selection?.size()
                Timber.e("onSelectionChanged, $nItems")
            }
        })

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

    private val actionModeCallback = object : ActionMode.Callback {
        // Called when the action mode is created; startActionMode() was called
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.menu_favourites_action_mode, menu)
            selectionModeOn = true
//            val animateIcon = activity?.findViewById<ImageView>(R.id.action_mode_close_button)
//            animateIcon?.let {
//                it.visibility = View.VISIBLE
//                (it.drawable as? Animatable)?.start()
//            }
            return true
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_garbage -> {
                    mode.finish() // Action picked, so close the CAB
                    true
                }
                else -> false
            }
        }

        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
//            val cancelButton = activity?.findViewById(R.id.action_mode_close_button) as ImageView?
//            val animation = AnimationUtils.loadAnimation(activity, R.anim.anim_backk)
//            val animateIcon = activity?.findViewById<ImageView>(R.id.action_mode_close_button)
//            animateIcon?.let {
//                it.visibility = View.VISIBLE
//                (it.drawable as? Animatable)?.start()
//            }
            selectionModeOn = false
            actionMode = null
        }
    }

}