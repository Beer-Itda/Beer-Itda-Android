package com.ddd4.synesthesia.beer.presentation.ui.common.related

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemBeerCardHorizontalBinding
import com.ddd4.synesthesia.beer.util.ext.createView

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