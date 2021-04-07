package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.select

import android.view.View
import com.ddd4.synesthesia.beer.databinding.ItemFilterSelectedAromaBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemViewModel

class AromaSelectedItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<AromaItemViewModel, ItemFilterSelectedAromaBinding>(itemView) {

    override fun onBind(viewModel: AromaItemViewModel, position: Int) {
        binding?.apply {
            aroma = viewModel
        }
    }
}