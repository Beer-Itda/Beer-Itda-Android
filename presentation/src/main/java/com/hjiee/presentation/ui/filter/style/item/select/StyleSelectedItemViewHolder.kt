package com.hjiee.presentation.ui.filter.style.item.select

import android.view.View
import com.hjiee.presentation.databinding.ItemFilterSelectedStyleBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.filter.style.item.small.StyleSmallItemViewModel

class StyleSelectedItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<StyleSmallItemViewModel, ItemFilterSelectedStyleBinding>(itemView) {

    override fun onBind(viewModel: StyleSmallItemViewModel, position: Int) {
        binding?.apply {
            style = viewModel
        }
    }
}