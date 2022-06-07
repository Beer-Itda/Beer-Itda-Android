package com.hjiee.presentation.ui.detail.item.info

import android.view.View
import android.view.ViewGroup
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ItemDetailInformationBinding
import com.hjiee.presentation.ui.detail.adapter.BeerDetailViewHolder
import com.hjiee.presentation.util.ext.createView

class BeerDetailInfoItemViewViewHolder(
    itemView: View
) : BeerDetailViewHolder<BeerDetailInfoItemViewModel, ItemDetailInformationBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            BeerDetailInfoItemViewViewHolder(parent.createView(R.layout.item_detail_information))
    }

    override fun onBind(viewModel: BeerDetailInfoItemViewModel, position: Int) {
        binding?.run {
            this.viewModel = viewModel
            executePendingBindings()
        }
    }
}