package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.parent.award

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemBeerAwardBinding
import com.ddd4.synesthesia.beer.ext.createView
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.HomeViewHolder

class BeerAwardItemViewHolder(
    itemView: View
) : HomeViewHolder<BeerAwardItemViewModel, ItemBeerAwardBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            BeerAwardItemViewHolder(parent.createView(R.layout.item_beer_award))
    }

    override fun onBind(viewModel: BeerAwardItemViewModel, position: Int) {
        binding?.run {
            beer = viewModel.beer.data
            executePendingBindings()
        }
    }
}