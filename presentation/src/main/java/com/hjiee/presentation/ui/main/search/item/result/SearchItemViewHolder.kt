package com.hjiee.presentation.ui.main.search.item.result

import android.view.View
import android.view.ViewGroup
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ItemAutoCompletationBinding
import com.hjiee.presentation.ui.main.search.item.SearchViewHolder
import com.hjiee.presentation.util.ext.createView


class SearchItemViewHolder(
    itemView: View
) : SearchViewHolder<SearchItemViewModel, ItemAutoCompletationBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            SearchItemViewHolder(parent.createView(R.layout.item_auto_completation))
    }

    override fun onBind(viewModel: SearchItemViewModel, position: Int) {
        binding?.run {
            this.viewModel = viewModel
            executePendingBindings()
        }
    }
}