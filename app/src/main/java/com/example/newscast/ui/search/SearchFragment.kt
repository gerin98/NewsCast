package com.example.newscast.ui.search

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
import com.example.newscast.databinding.FragmentSearchBinding
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.ui.ViewModelFactory
import com.example.newscast.ui.adapter.SearchAdapter
import com.example.newscast.ui.browse.BrowseActivity
import com.example.newscast.ui.newspaper.NewsPaperActivity

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels { ViewModelFactory() }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataset: ArrayList<ResultsModel?>

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate<FragmentSearchBinding>(inflater, R.layout.fragment_search, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SearchFragment.viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        initLiveData()
    }

    private fun initLiveData() {
        viewModel.searchLiveData.observe(viewLifecycleOwner, newsLiveDataObserver)
    }

    private fun setupRecyclerView(view: View) {
        dataset = ArrayList()

        viewManager = LinearLayoutManager(activity)
        viewAdapter = SearchAdapter(dataset) {
            recyclerViewOnClick(it)
        }

        val dividerItemDecoration = DividerItemDecoration(activity, LinearLayout.VERTICAL).apply {
            activity?.getDrawable(R.drawable.divider)?.let {
                setDrawable(it)
            }
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.searchRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun recyclerViewOnClick(item: ResultsModel?) {
        val intent = Intent(activity, NewsPaperActivity::class.java)
        intent.putExtra(BrowseActivity.NEWS_ARTICLE_INTENT_FLAGS, item)
        startActivity(intent)
    }

}
