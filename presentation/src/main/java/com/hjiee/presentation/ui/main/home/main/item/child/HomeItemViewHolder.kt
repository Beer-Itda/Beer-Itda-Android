package com.hjiee.presentation.ui.main.home.main.item.child

import android.view.View
import android.view.ViewGroup
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ItemBeerCardHorizontalBinding
import com.hjiee.presentation.util.ext.createView

class HomeItemViewHolder(
    itemView: View
) : HomeViewChildHolder<HomeBeerChildItemViewModel, ItemBeerCardHorizontalBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            HomeItemViewHolder(parent.createView(R.layout.item_beer_card_horizontal))
    }

    override fun onBind(viewModel: HomeBeerChildItemViewModel, position: Int) {
        binding?.run {
            beer = viewModel.data
            executePendingBindings()
        }
    }
}