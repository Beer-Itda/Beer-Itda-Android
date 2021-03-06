package com.ddd4.synesthesia.beer.presentation.ui.detail.item.related

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemDetailRelatedBinding
import com.ddd4.synesthesia.beer.presentation.ui.detail.adapter.BeerDetailViewHolder
import com.ddd4.synesthesia.beer.util.ext.createView

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