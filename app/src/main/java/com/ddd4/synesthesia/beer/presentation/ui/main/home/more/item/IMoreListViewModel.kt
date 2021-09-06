package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.item

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.view.MoreListViewType

interface IMoreListViewModel : IViewTypeGetter<MoreListViewType> {
    override fun getViewType(): MoreListViewType {
        return when (this) {
            is MoreListItemViewModel -> MoreListViewType.LIST
            else -> MoreListViewType.LOADING
        }
    }
}