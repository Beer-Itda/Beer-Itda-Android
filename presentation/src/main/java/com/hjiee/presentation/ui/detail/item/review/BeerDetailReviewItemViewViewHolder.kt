package com.hjiee.presentation.ui.detail.item.review

import android.view.View
import android.view.ViewGroup
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ItemDetailReviewBinding
import com.hjiee.presentation.ui.detail.adapter.BeerDetailViewHolder
import com.hjiee.presentation.util.ext.createView

class BeerDetailReviewItemViewViewHolder(
    itemView: View
) : BeerDetailViewHolder<BeerDetailReviewListViewModel, ItemDetailReviewBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            BeerDetailReviewItemViewViewHolder(parent.createView(R.layout.item_detail_review))
    }

    override fun onBind(viewModel: BeerDetailReviewListViewModel, position: Int) {
        binding?.run {
            this.viewModel = viewModel
            executePendingBindings()
        }
    }
}