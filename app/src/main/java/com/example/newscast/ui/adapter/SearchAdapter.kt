package com.example.newscast.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.utils.glide.loadThumbnailFromUrl

class SearchAdapter(private val newsDataset: ArrayList<ResultsModel?>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsTileLayout: FrameLayout = view.findViewById(R.id.news_tile_layout)
        val newsTileName: TextView = view.findViewById(R.id.news_tile_title)
        val newsTileSource: TextView = view.findViewById(R.id.news_tile_source)
        val newsTileImage: ImageView = view.findViewById(R.id.news_tile_image)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // create a new view based on the view type
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_tile, parent, false) as View
        return SearchViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as SearchViewHolder

        // load image
        val imageUrl = newsDataset[position]?.image
        if (imageUrl != null) {
            // set unique transition name
            ViewCompat.setTransitionName(viewHolder.newsTileImage, newsDataset[position]?.uri)
            viewHolder.newsTileImage.loadThumbnailFromUrl(viewHolder.newsTileImage.context, imageUrl)
        } else {
            viewHolder.newsTileImage.setImageDrawable(null)
        }

        // load title
        viewHolder.newsTileName.text = newsDataset[position]?.title

        // load source
        viewHolder.newsTileSource.text = newsDataset[position]?.source?.title
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = newsDataset.size

}