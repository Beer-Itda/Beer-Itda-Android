package com.hjiee.presentation.ui.filter.aroma.item.small

import android.view.View
import com.hjiee.presentation.databinding.ItemFilterAromaBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder

class AromaItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<AromaItemViewModel, ItemFilterAromaBinding>(itemView) {

    override fun onBind(viewModel: AromaItemViewModel, position: Int) {
        binding?.apply {
            aroma = viewModel
        }
    }
}