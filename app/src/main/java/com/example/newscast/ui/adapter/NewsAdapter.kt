package com.example.newscast.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newscast.R
import com.example.newscast.network.model.ResultsModel
import com.example.newscast.ui.adapter.NewsAdapter.ViewHolder
import com.example.newscast.utils.glide.GlideApp
import com.example.newscast.utils.glide.miniThumbnail


class NewsAdapter(private  val newsDataset: ArrayList<ResultsModel?>) : RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val newsTileName: TextView = view.findViewById(R.id.news_tile_title)
        val newsTileSource: TextView = view.findViewById(R.id.news_tile_source)
        val newsTileImage: ImageView = view.findViewById(R.id.news_tile_image)

        override fun onClick(p0: View?) {
            // handle clicks
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_tile, parent, false) as View

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        // load image
        val imageUrl = newsDataset[position]?.image
        if (imageUrl != null) {
            GlideApp.with(holder.newsTileImage.context)
                .load(imageUrl)
                .miniThumbnail()
                .into(holder.newsTileImage)
        } else {
            holder.newsTileImage.setImageDrawable(null)
        }

        // load title
        holder.newsTileName.text = newsDataset[position]?.title

        // load source
        holder.newsTileSource.text = newsDataset[position]?.source?.title

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = newsDataset.size

}