package com.ddd4.synesthesia.beer.presentation.ui.main.search.item

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter
import com.ddd4.synesthesia.beer.presentation.ui.main.search.view.SearchViewType

interface ISearchViewModel : IViewTypeGetter<SearchViewType> {
    override fun getViewType(): SearchViewType {
        return when (this) {
            is SearchItemViewModel -> SearchViewType.SEARCH
            else -> SearchViewType.SEARCH
        }
    }
}