package com.ddd4.synesthesia.beer.presentation.ui.filter.item.small

import android.view.View
import com.ddd4.synesthesia.beer.databinding.ItemStyleSetBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder

class StyleSmalItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<StyleSmallItemViewModel, ItemStyleSetBinding>(itemView) {

    override fun onBind(viewModel: StyleSmallItemViewModel, position: Int) {
        binding?.apply {
            filter = viewModel
        }
    }
}