package com.hjiee.presentation.ui.common.review

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindableAdapter
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder

class ReviewListAdapter : BaseBindableAdapter<ReviewListViewType, IReviewListViewModel>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<IReviewListViewModel, ViewDataBinding> {
        return ReviewListViewHolder.getViewHolder(parent, ReviewListViewType.values()[viewType])
    }
}

enum class ReviewListViewType {
    REVIEW
}