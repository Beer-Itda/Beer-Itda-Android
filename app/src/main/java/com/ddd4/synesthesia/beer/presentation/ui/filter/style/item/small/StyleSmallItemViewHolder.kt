package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small

import android.view.View
import com.ddd4.synesthesia.beer.databinding.ItemFilterStyleSmallBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder

class StyleSmallItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<StyleSmallItemViewModel, ItemFilterStyleSmallBinding>(itemView) {

    override fun onBind(viewModel: StyleSmallItemViewModel, position: Int) {
        binding?.apply {
            style = viewModel
        }
    }
}