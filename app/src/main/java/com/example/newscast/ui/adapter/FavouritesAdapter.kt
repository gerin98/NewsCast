package com.example.newscast.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.data.room.Articles
import com.example.newscast.di.ResourceHelper
import com.example.newscast.utils.glide.loadThumbnailFromUrl
import timber.log.Timber

class FavouritesAdapter(private val newsDataset: ArrayList<Articles?>,
                        private val resourceHelper: ResourceHelper)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    val selectedItems = HashSet<String>()
    private var tracker: SelectionTracker<Long>? = null

    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }

    fun removeTracker() {
        this.tracker = null
    }

    class FavouritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsTileLayout: FrameLayout = view.findViewById(R.id.news_tile_layout)
        val newsTileName: TextView = view.findViewById(R.id.news_tile_title)
        val newsTileSource: TextView = view.findViewById(R.id.news_tile_source)
        val newsTileImage: ImageView = view.findViewById(R.id.news_tile_image)

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> = object: ItemDetailsLookup.ItemDetails<Long>() {
            override fun getSelectionKey(): Long? = itemId
            override fun getPosition(): Int = adapterPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_tile, parent, false) as View
        return FavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as FavouritesViewHolder

        // load image
        val imageUrl = newsDataset[position]?.imageUrl
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
        viewHolder.newsTileSource.text = newsDataset[position]?.author

        if (tracker?.isSelected(position.toLong()) == true) {
            Timber.d("item selected: $position")
            holder.newsTileLayout.background = ColorDrawable(resourceHelper.getColor(R.color.accent5))
            newsDataset[position]?.uri?.also {
                selectedItems.add(it)
            }
        } else {
            Timber.d("item un-selected: $position")
            holder.newsTileLayout.background = ColorDrawable(Color.WHITE)
            newsDataset[position]?.uri?.also {
                selectedItems.remove(it)
            }
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = newsDataset.size

    override fun getItemId(position: Int): Long = position.toLong()

}
