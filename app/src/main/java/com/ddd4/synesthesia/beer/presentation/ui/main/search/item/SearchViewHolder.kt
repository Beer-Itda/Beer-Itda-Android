package com.ddd4.synesthesia.beer.presentation.ui.main.search.item

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.main.search.item.loading.LoadingItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.main.search.item.result.SearchItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.main.search.view.SearchViewType


abstract class SearchViewHolder<VM : Any, B : ViewDataBinding>(
    itemView: View
) : BaseBindingViewHolder<VM, B>(itemView) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(
            parent: ViewGroup,
            viewType: SearchViewType
        ): SearchViewHolder<ISearchViewModel, ViewDataBinding> {
            return when (viewType) {
                SearchViewType.SEARCH -> SearchItemViewHolder.newInstance(parent)
                SearchViewType.LOADING -> LoadingItemViewHolder.newInstance(parent)
            } as SearchViewHolder<ISearchViewModel, ViewDataBinding>
        }
    }
}