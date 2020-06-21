package com.example.newscast.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.utils.glide.GlideApp
import com.example.newscast.utils.glide.miniThumbnail


class NewsAdapter(
        private val newsDataset: ArrayList<ResultsModel?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val SMALL_TILE = 0
    private val LARGE_TILE = 1

    class SmallViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val smallNewsTileLayout: FrameLayout = view.findViewById(R.id.news_tile_layout)
        val smallNewsTileName: TextView = view.findViewById(R.id.news_tile_title)
        val smallNewsTileSource: TextView = view.findViewById(R.id.news_tile_source)
        val smallNewsTileImage: ImageView = view.findViewById(R.id.news_tile_image)
    }

    class LargeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val largeNewsTileLayout: LinearLayout = view.findViewById(R.id.news_tile_large_layout)
        val largeNewsTileName: TextView = view.findViewById(R.id.news_tile_large_title)
        val largeNewsTileSource: TextView = view.findViewById(R.id.news_tile_large_source)
        val largeNewsTileImage: ImageView = view.findViewById(R.id.news_tile_image)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLargeTile(position)) {
            LARGE_TILE
        } else {
            SMALL_TILE
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // create a new view based on the view type

        return if (viewType == LARGE_TILE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_tile_large, parent, false) as View
            LargeViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_tile, parent, false) as View
            SmallViewHolder(view)
        }

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (isLargeTile(position)) {
            // show large tile

            val largeHolder = holder as LargeViewHolder

            ViewCompat.setTransitionName(largeHolder.largeNewsTileImage, newsDataset[position]?.uri)

            // set item focus state
            largeHolder.largeNewsTileLayout.isSelected = true

            // load image
            val imageUrl = newsDataset[position]?.image
            if (imageUrl != null) {
                GlideApp.with(largeHolder.largeNewsTileImage.context)
                    .load(imageUrl)
                    .into(largeHolder.largeNewsTileImage)
            } else {
                largeHolder.largeNewsTileImage.setImageDrawable(null)
            }

            // load title
            largeHolder.largeNewsTileName.text = newsDataset[position]?.title

            // load source
            largeHolder.largeNewsTileSource.text = newsDataset[position]?.source?.title
        } else {
            // show small tile

            val smallHolder = holder as SmallViewHolder

            ViewCompat.setTransitionName(smallHolder.smallNewsTileImage, newsDataset[position]?.uri)

            // set item focus state
            smallHolder.smallNewsTileLayout.isSelected = true

            // load image
            val imageUrl = newsDataset[position]?.image
            if (imageUrl != null) {
                GlideApp.with(smallHolder.smallNewsTileImage.context)
                    .load(imageUrl)
                    .miniThumbnail()
                    .into(smallHolder.smallNewsTileImage)
            } else {
                smallHolder.smallNewsTileImage.setImageDrawable(null)
            }

            // load title
            smallHolder.smallNewsTileName.text = newsDataset[position]?.title

            // load source
            smallHolder.smallNewsTileSource.text = newsDataset[position]?.source?.title
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = newsDataset.size

    /**
     * Every 7th tile starting from the 4th tile should be a large tile
     */
    private fun isLargeTile(position: Int): Boolean {
        return position%7 == 3
    }

}