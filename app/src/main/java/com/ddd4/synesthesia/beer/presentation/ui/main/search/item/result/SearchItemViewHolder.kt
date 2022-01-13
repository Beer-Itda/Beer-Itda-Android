package com.ddd4.synesthesia.beer.presentation.ui.main.search.item.result

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemAutoCompletationBinding
import com.ddd4.synesthesia.beer.presentation.ui.main.search.item.SearchViewHolder
import com.ddd4.synesthesia.beer.util.ext.createView


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