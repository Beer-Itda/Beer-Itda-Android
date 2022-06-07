package com.hjiee.presentation.ui.main.search.item.loading

import android.view.View
import android.view.ViewGroup
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.LayoutLoadingBinding
import com.hjiee.presentation.ui.main.search.item.SearchViewHolder
import com.hjiee.presentation.util.ext.createView


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