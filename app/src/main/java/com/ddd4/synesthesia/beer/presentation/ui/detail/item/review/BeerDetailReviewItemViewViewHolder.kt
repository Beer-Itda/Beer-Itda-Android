package com.ddd4.synesthesia.beer.presentation.ui.detail.item.review

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemDetailReviewBinding
import com.ddd4.synesthesia.beer.presentation.ui.detail.adapter.BeerDetailViewHolder
import com.ddd4.synesthesia.beer.util.ext.createView

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