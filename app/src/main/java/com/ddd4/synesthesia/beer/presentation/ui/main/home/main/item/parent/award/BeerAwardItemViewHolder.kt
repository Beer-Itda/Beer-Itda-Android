package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.parent.award

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemHomeBeerAwardBinding
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.HomeViewHolder
import com.ddd4.synesthesia.beer.util.ext.createView

class BeerAwardItemViewHolder(
    itemView: View
) : HomeViewHolder<BeerAwardItemViewModel, ItemHomeBeerAwardBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            BeerAwardItemViewHolder(parent.createView(R.layout.item_home_beer_award))
    }

    override fun onBind(viewModel: BeerAwardItemViewModel, position: Int) {
        binding?.run {
            beer = viewModel.beer
            executePendingBindings()
        }
    }
}