package com.hjiee.presentation.ui.common.review

import android.view.View
import android.view.ViewGroup
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ItemReviewBinding
import com.hjiee.presentation.util.ext.createView

class ReviewItemViewViewHolder(
    itemView: View
) : ReviewListViewHolder<ReviewItemViewModel, ItemReviewBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            ReviewItemViewViewHolder(parent.createView(R.layout.item_review))
    }

    override fun onBind(viewModel: ReviewItemViewModel, position: Int) {
        binding?.run {
            this.item = viewModel
            executePendingBindings()
        }
    }
}