package com.example.newscast.ui.browse

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.databinding.FragmentBrowseBinding
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.ui.ViewModelFactory
import com.example.newscast.ui.adapter.NewsAdapter
import com.example.newscast.ui.newspaper.NewsPaperActivity

class BrowseFragment : Fragment() {

    private val viewModel: BrowseViewModel by activityViewModels { ViewModelFactory() }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataset: ArrayList<ResultsModel?>
    private var newsTopic = "Breaking News"

    // Observers
    private val newsLiveDataObserver = Observer<List<ResultsModel?>?> {
        dataset.clear()

        if (it != null) {
            for (result in it) {
                dataset.add(result)
            }
        }

        viewAdapter.notifyDataSetChanged()
    }

    private val newsTopicLiveDataObserver = Observer<String?> {
        it?.let {
            newsTopic = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentBrowseBinding = DataBindingUtil.inflate<FragmentBrowseBinding>(inflater, R.layout.fragment_browse, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@BrowseFragment.viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        initLiveData()
    }

    private fun initLiveData() {
        viewModel.newsLiveData.observe(viewLifecycleOwner, newsLiveDataObserver)
        viewModel.newsTopic.observe(viewLifecycleOwner, newsTopicLiveDataObserver)
    }

    private fun setupRecyclerView(view: View) {
        dataset = ArrayList()

        viewManager = LinearLayoutManager(activity)
        viewAdapter = NewsAdapter(dataset) {
            recyclerViewOnClick(it)
        }

        val dividerItemDecoration = DividerItemDecoration(activity, LinearLayout.VERTICAL).apply {
            activity?.getDrawable(R.drawable.divider)?.let {
                setDrawable(it)
            }
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.newsRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun recyclerViewOnClick(item: ResultsModel?) {
        val intent = Intent(activity, NewsPaperActivity::class.java)
        intent.putExtra(BrowseActivity.NEWS_ARTICLE_INTENT_FLAGS, item)
        intent.putExtra(BrowseActivity.NEWS_TOPIC_INTENT_FLAGS, newsTopic)
        startActivity(intent)
    }

}
