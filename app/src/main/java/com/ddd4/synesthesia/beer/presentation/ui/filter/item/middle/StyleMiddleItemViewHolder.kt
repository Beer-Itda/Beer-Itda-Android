package com.ddd4.synesthesia.beer.presentation.ui.filter.item.middle

import android.view.View
import com.ddd4.synesthesia.beer.databinding.ItemFilterMiddleBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder

class StyleMiddleItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<StyleMiddleItemViewModel, ItemFilterMiddleBinding>(itemView) {

    override fun onBind(viewModel: StyleMiddleItemViewModel, position: Int) {
        binding?.apply {
            filter = viewModel
        }
    }
}