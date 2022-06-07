package com.hjiee.presentation.util.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class HorizontalItemSpaceDecoration(
    private val spaceWidth: Int,
    private val headTailSpaceWidth: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        parent.getChildViewHolder(view)
        if (position == 0) {
            outRect.left = headTailSpaceWidth
            outRect.right = 0
        } else if (position == parent.adapter!!.itemCount - 1) {
            outRect.left = spaceWidth
            outRect.right = headTailSpaceWidth
        } else {
            outRect.left = spaceWidth
            outRect.right = 0
        }
    }
}