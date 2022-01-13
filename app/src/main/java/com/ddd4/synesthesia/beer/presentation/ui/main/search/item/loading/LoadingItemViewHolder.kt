package com.ddd4.synesthesia.beer.presentation.ui.main.search.item.loading

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutLoadingBinding
import com.ddd4.synesthesia.beer.presentation.ui.main.search.item.SearchViewHolder
import com.ddd4.synesthesia.beer.util.ext.createView


class LoadingItemViewHolder(
    itemView: View
) : SearchViewHolder<LoadingItemViewModel, LayoutLoadingBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            LoadingItemViewHolder(parent.createView(R.layout.layout_loading))
    }

    override fun onBind(viewModel: LoadingItemViewModel, position: Int) {
        binding?.run {
            executePendingBindings()
        }
    }
}