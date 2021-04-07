package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.select

import android.view.View
import com.ddd4.synesthesia.beer.databinding.ItemFilterSelectedStyleBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel

class StyleSelectedItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<StyleSmallItemViewModel, ItemFilterSelectedStyleBinding>(itemView) {

    override fun onBind(viewModel: StyleSmallItemViewModel, position: Int) {
        binding?.apply {
            style = viewModel
        }
    }
}