package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.item

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemBeerCardVerticalBinding
import com.ddd4.synesthesia.beer.util.ext.createView


class MyFavoriteItemViewHolder(
    itemView: View
) : MyFavoriteViewHolder<MyFavoriteItemViewModel, ItemBeerCardVerticalBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            MyFavoriteItemViewHolder(parent.createView(R.layout.item_beer_card_vertical))
    }

    override fun onBind(viewModel: MyFavoriteItemViewModel, position: Int) {
        binding?.run {
            beer = viewModel.beer
            executePendingBindings()
        }
    }
}