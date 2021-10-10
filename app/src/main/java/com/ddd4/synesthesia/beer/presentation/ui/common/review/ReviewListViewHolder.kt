package com.ddd4.synesthesia.beer.presentation.ui.common.review

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder

abstract class ReviewListViewHolder<VM : Any, B : ViewDataBinding>(
    itemView: View
) : BaseBindingViewHolder<VM, B>(itemView) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(
            parent: ViewGroup,
            viewType: ReviewListViewType
        ): ReviewListViewHolder<IReviewListViewModel, ViewDataBinding> {
            return when (viewType) {
                ReviewListViewType.REVIEW -> ReviewItemViewViewHolder.newInstance(parent)
            } as ReviewListViewHolder<IReviewListViewModel, ViewDataBinding>
        }
    }
}