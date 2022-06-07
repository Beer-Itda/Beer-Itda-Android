package com.hjiee.presentation.ui.filter.style.item.small

import android.view.View
import com.hjiee.presentation.databinding.ItemFilterStyleSmallBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder

class StyleSmallItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<StyleSmallItemViewModel, ItemFilterStyleSmallBinding>(itemView) {

    override fun onBind(viewModel: StyleSmallItemViewModel, position: Int) {
        binding?.apply {
            style = viewModel
        }
    }
}