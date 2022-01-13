package com.ddd4.synesthesia.beer.presentation.ui.main.search.item

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter
import com.ddd4.synesthesia.beer.presentation.ui.main.search.item.loading.LoadingItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.search.item.result.SearchItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.search.view.SearchViewType

interface ISearchViewModel : IViewTypeGetter<SearchViewType> {
    override fun getViewType(): SearchViewType {
        return when (this) {
            is SearchItemViewModel -> SearchViewType.SEARCH
            is LoadingItemViewModel -> SearchViewType.LOADING
            else -> SearchViewType.SEARCH
        }
    }
}