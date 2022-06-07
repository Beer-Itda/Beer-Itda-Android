package com.hjiee.presentation.ui.filter.aroma.item.select

import android.view.View
import com.hjiee.presentation.databinding.ItemFilterSelectedAromaBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.filter.aroma.item.small.AromaItemViewModel

class AromaSelectedItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<AromaItemViewModel, ItemFilterSelectedAromaBinding>(itemView) {

    override fun onBind(viewModel: AromaItemViewModel, position: Int) {
        binding?.apply {
            aroma = viewModel
        }
    }
}