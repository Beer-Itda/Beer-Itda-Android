package com.hjiee.presentation.ui.main.search.view

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindableAdapter
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.main.search.item.ISearchViewModel
import com.hjiee.presentation.ui.main.search.item.SearchViewHolder

class SearchAdapter : BaseBindableAdapter<SearchViewType, ISearchViewModel>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<ISearchViewModel, ViewDataBinding> {
        return SearchViewHolder.getViewHolder(parent, SearchViewType.values()[viewType])
    }
}

enum class SearchViewType {
    SEARCH,
    LOADING
}