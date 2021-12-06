package com.ddd4.synesthesia.beer.presentation.ui.detail.item.info

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemDetailInformationBinding
import com.ddd4.synesthesia.beer.util.ext.createView
import com.ddd4.synesthesia.beer.presentation.ui.detail.adapter.BeerDetailViewHolder

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