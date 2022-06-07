package com.hjiee.presentation.ui.common.related

import android.view.View
import android.view.ViewGroup
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ItemBeerCardHorizontalBinding
import com.hjiee.presentation.util.ext.createView

class RelatedItemViewViewHolder(
    itemView: View
) : RelatedListViewHolder<RelatedItemViewModel, ItemBeerCardHorizontalBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            RelatedItemViewViewHolder(parent.createView(R.layout.item_beer_card_horizontal))
    }

    override fun onBind(viewModel: RelatedItemViewModel, position: Int) {
        binding?.run {
            beer = viewModel.beer
            executePendingBindings()
        }
    }
}