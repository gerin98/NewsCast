package com.example.newscast.ui.adapter

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

// ItemDetails lookup to be used by SelectionTracker in a RecyclerView
class RecyclerViewLookup(private val recyclerView: RecyclerView): ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if(view != null) {
            return (recyclerView.getChildViewHolder(view) as FavouritesAdapter.FavouritesViewHolder)
                .getItemDetails()
        }
        return null
    }
}