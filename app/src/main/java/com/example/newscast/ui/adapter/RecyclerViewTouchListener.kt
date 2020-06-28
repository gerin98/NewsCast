package com.example.newscast.ui.adapter

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener

class RecyclerViewTouchListener(context: Context?, listener: OnTouchEventListener?)
    : SimpleOnItemTouchListener() {

    interface OnTouchEventListener {
        fun onClick(clickedView: View?, adapterPosition: Int)
        fun onDoubleClick(doubleClickedView: View?, adapterPosition: Int)
        fun onLongPress(longPressedView: View?, adapterPosition: Int)
    }

    private inner class GestureListener : SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (onTouchEventListener != null) {
                if (childView != null) {
                    onTouchEventListener.onClick(childView, childViewAdapterPosition)
                    return true
                }
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (onTouchEventListener != null) {
                if (childView != null) {
                    onTouchEventListener.onDoubleClick(childView, childViewAdapterPosition)
                    return true
                }
            }
            return false
        }

        override fun onLongPress(e: MotionEvent) {
            if (onTouchEventListener != null && childView != null) {
                onTouchEventListener.onLongPress(childView, childViewAdapterPosition)
            }
        }

    }

    private val onTouchEventListener: OnTouchEventListener?
    private val gestureDetector: GestureDetector
    private var childView: View? = null
    private var childViewAdapterPosition = 0

    init {
        gestureDetector = GestureDetector(context, GestureListener())
        onTouchEventListener = listener
    }

    override fun onInterceptTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.x
        val y = motionEvent.y
        childView = recyclerView.findChildViewUnder(x, y)
        childView?.let {
            val pos = recyclerView.getChildAdapterPosition(it)
            childViewAdapterPosition = pos
            gestureDetector.onTouchEvent(motionEvent)
        }
        return false
    }

}