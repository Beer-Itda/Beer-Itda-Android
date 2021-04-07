package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small

import android.view.View
import com.ddd4.synesthesia.beer.databinding.ItemFilterAromaBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder

class AromaItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<AromaItemViewModel, ItemFilterAromaBinding>(itemView) {

    override fun onBind(viewModel: AromaItemViewModel, position: Int) {
        binding?.apply {
            aroma = viewModel
        }
    }
}