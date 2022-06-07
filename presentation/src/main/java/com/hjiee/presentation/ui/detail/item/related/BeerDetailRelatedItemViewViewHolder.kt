package com.hjiee.presentation.ui.detail.item.related

import android.view.View
import android.view.ViewGroup
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ItemDetailRelatedBinding
import com.hjiee.presentation.ui.detail.adapter.BeerDetailViewHolder
import com.hjiee.presentation.util.ext.createView

class BeerDetailRelatedItemViewViewHolder(
    itemView: View
) : BeerDetailViewHolder<BeerDetailRelatedListViewModel, ItemDetailRelatedBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            BeerDetailRelatedItemViewViewHolder(parent.createView(R.layout.item_detail_related))
    }

    override fun onBind(viewModel: BeerDetailRelatedListViewModel, position: Int) {
        binding?.run {
            this.viewModel = viewModel
            executePendingBindings()
        }
    }
}