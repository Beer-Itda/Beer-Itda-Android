package com.hjiee.presentation.util.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class VerticalItemSpaceDecoration(
    private val spaceHeight: Int,
    private val topBottomSpaceHeight: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.top = topBottomSpaceHeight
            if (parent.adapter!!.itemCount == 1) {
                outRect.bottom = 0
            } else {
                outRect.bottom = spaceHeight
            }
        } else if (position == parent.adapter!!.itemCount - 1) {
            outRect.top = 0
            outRect.bottom = topBottomSpaceHeight
        } else {
            outRect.top = 0
            outRect.bottom = spaceHeight
        }
    }
}