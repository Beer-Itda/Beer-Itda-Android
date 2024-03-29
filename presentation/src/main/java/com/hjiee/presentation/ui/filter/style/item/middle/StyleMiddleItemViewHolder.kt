package com.hjiee.presentation.ui.filter.style.item.middle

import android.view.View
import com.hjiee.presentation.databinding.ItemFilterStyleMiddleBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder

class StyleMiddleItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<StyleMiddleItemViewModel, ItemFilterStyleMiddleBinding>(itemView) {

    override fun onBind(viewModel: StyleMiddleItemViewModel, position: Int) {
        binding?.apply {
            style = viewModel
        }
    }
}