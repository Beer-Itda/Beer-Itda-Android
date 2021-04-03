package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.select

import android.view.View
import com.ddd4.synesthesia.beer.databinding.ItemSelectedStyleBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel

class StyleSelectedItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<StyleSmallItemViewModel, ItemSelectedStyleBinding>(itemView) {

    override fun onBind(viewModel: StyleSmallItemViewModel, position: Int) {
        binding?.apply {
            style = viewModel
        }
    }
}