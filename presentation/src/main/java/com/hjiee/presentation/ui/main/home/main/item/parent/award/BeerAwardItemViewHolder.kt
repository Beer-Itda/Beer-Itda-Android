package com.hjiee.presentation.ui.main.home.main.item.parent.award

import android.view.View
import android.view.ViewGroup
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ItemHomeBeerAwardBinding
import com.hjiee.presentation.ui.main.home.main.item.HomeViewHolder
import com.hjiee.presentation.util.ext.createView

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