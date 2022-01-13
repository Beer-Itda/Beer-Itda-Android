package com.ddd4.synesthesia.beer.presentation.ui.main.search.view

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindableAdapter
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.main.search.item.ISearchViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.search.item.SearchViewHolder

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