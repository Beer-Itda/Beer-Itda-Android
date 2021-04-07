package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle

import android.view.View
import com.ddd4.synesthesia.beer.databinding.ItemFilterStyleMiddleBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder

class StyleMiddleItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<StyleMiddleItemViewModel, ItemFilterStyleMiddleBinding>(itemView) {

    override fun onBind(viewModel: StyleMiddleItemViewModel, position: Int) {
        binding?.apply {
            style = viewModel
        }
    }
}