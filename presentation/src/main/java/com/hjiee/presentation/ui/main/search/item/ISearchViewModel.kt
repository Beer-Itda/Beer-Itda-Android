package com.hjiee.presentation.ui.main.search.item

import com.hjiee.presentation.base.recyclerview2.IViewTypeGetter
import com.hjiee.presentation.ui.main.search.item.loading.LoadingItemViewModel
import com.hjiee.presentation.ui.main.search.item.result.SearchItemViewModel
import com.hjiee.presentation.ui.main.search.view.SearchViewType

interface ISearchViewModel : IViewTypeGetter<SearchViewType> {
    override fun getViewType(): SearchViewType {
        return when (this) {
            is SearchItemViewModel -> SearchViewType.SEARCH
            is LoadingItemViewModel -> SearchViewType.LOADING
            else -> SearchViewType.SEARCH
        }
    }
}