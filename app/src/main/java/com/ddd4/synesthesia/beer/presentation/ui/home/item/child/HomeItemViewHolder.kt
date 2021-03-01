package com.ddd4.synesthesia.beer.presentation.ui.home.item.child

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemBeerCardBinding
import com.ddd4.synesthesia.beer.ext.createView
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel

class HomeItemViewHolder(
    itemView: View
) : HomeViewChildHolder<BeerItemViewModel, ItemBeerCardBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) = HomeItemViewHolder(parent.createView(R.layout.item_beer_card))
    }

    override fun onBind(viewModel: BeerItemViewModel, position: Int) {
        binding?.run {
            beer = viewModel
            executePendingBindings()
        }
    }
}