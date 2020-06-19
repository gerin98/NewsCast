package com.example.newscast.ui.adapter

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener

// todo: gerin
/**
 * Created by Rany Albeg Wein on 07/11/2015.
 * Since we currently don't have a LongItemClick listener for the [RecyclerView]
 * this is an implementation that uses a [GestureDetector] to notify us for long click events on one of
 * [RecyclerView]'s child views. It also handles single-tap-up ( a click ) and a double-click on a child view.
 */
class RecyclerViewItemTouchListener(
    context: Context?,
    listener: OnItemClickEventListener?
) : SimpleOnItemTouchListener() {

    /**
     * A listener that will be invoked on item click events.
     */
    private val mOnItemClickListener: OnItemClickEventListener?

    /**
     * A gesture detector to detect and capture click events.
     */
    private val mGestureDetector: GestureDetector

    init {
        mGestureDetector = GestureDetector(context, GestureDelegator())
        mOnItemClickListener = listener
    }

    /**
     * The child on which a click event happened.
     */
    private var mChildView: View? = null

    /**
     * The position of #mChildView in the adapter.
     */
    private var mChildViewAdapterPosition = 0
    override fun onInterceptTouchEvent(
        recyclerView: RecyclerView,
        motionEvent: MotionEvent
    ): Boolean {
        val x = motionEvent.x
        val y = motionEvent.y
        mChildView = recyclerView.findChildViewUnder(x, y)
        mChildView?.let {
            val pos = recyclerView.getChildAdapterPosition(it)
            mChildViewAdapterPosition = pos
            mGestureDetector.onTouchEvent(motionEvent)
        }
        return false
    }

    /**
     * A listener for [RecyclerView] click events.
     */
    interface OnItemClickEventListener {
        /**
         * Called when an item is long clicked.
         * @param longClickedView The long clicked view.
         * @param adapterPosition The position of the long clicked view in the adapter.
         */
        fun onItemLongClick(longClickedView: View?, adapterPosition: Int)

        /**
         * Called when an item is clicked.
         * @param clickedView The clicked view.
         * @param adapterPosition The position of the clicked view in the adapter.
         */
        fun onItemClick(clickedView: View?, adapterPosition: Int)

        /**
         * Called when an item is double clicked.
         * @param doubleClickedView The clicked view.
         * @param adapterPosition The position of the clicked view in the adapter.
         */
        fun onItemDoubleClick(doubleClickedView: View?, adapterPosition: Int)
    }

    /**
     * An implementation of a [GestureDetector.SimpleOnGestureListener] that will handle
     * [GestureDetector.SimpleOnGestureListener.onLongPress]
     * [GestureDetector.SimpleOnGestureListener.onSingleTapConfirmed], and
     * [GestureDetector.SimpleOnGestureListener.onDoubleTap]
     * methods and will invoke
     * [OnItemClickEventListener.onItemLongClick]
     * [OnItemClickEventListener.onItemClick], and
     * [OnItemClickEventListener.onItemDoubleClick]
     * accordingly.
     */
    private inner class GestureDelegator : SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent) {
            if (mOnItemClickListener != null) {
                if (mChildView != null) {
                    mOnItemClickListener.onItemLongClick(mChildView, mChildViewAdapterPosition)
                }
            }
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (mOnItemClickListener != null) {
                if (mChildView != null) {
                    mOnItemClickListener.onItemDoubleClick(mChildView, mChildViewAdapterPosition)
                    return true
                }
            }
            return false
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            /**
             * Unlike OnGestureListener.onSingleTapUp(MotionEvent), this will only be called after the detector
             * is confident that the user's first tap is not followed by a second tap leading to a double-tap gesture.
             */
            if (mOnItemClickListener != null) {
                if (mChildView != null) {
                    mOnItemClickListener.onItemClick(mChildView, mChildViewAdapterPosition)
                    return true
                }
            }
            return false
        }
    }

}