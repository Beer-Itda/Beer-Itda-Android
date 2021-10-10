package com.ddd4.synesthesia.beer.presentation.ui.common.review

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemReviewBinding
import com.ddd4.synesthesia.beer.ext.createView

class ReviewItemViewViewHolder(
    itemView: View
) : ReviewListViewHolder<ReviewItemViewModel, ItemReviewBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            ReviewItemViewViewHolder(parent.createView(R.layout.item_review))
    }

    override fun onBind(viewModel: ReviewItemViewModel, position: Int) {
        binding?.run {
            this.review = viewModel
            executePendingBindings()
        }
    }
}