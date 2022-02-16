package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.item

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemBeerCardVerticalBinding
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.view.MoreListHolder
import com.ddd4.synesthesia.beer.util.ext.createView

class MoreListItemViewHolder constructor(
    itemView: View
) : MoreListHolder<MoreListItemViewModel, ItemBeerCardVerticalBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            MoreListItemViewHolder(parent.createView(R.layout.item_beer_card_vertical))
    }

    override fun onBind(viewModel: MoreListItemViewModel, position: Int) {
        binding?.run {
            beer = viewModel.beer
            executePendingBindings()
        }
    }
}