package com.hjiee.presentation.ui.main.home.more.item

import com.hjiee.presentation.base.recyclerview2.IViewTypeGetter
import com.hjiee.presentation.ui.main.home.more.view.MoreListViewType

interface IMoreListViewModel : IViewTypeGetter<MoreListViewType> {
    override fun getViewType(): MoreListViewType {
        return when (this) {
            is MoreListItemViewModel -> MoreListViewType.LIST
            else -> MoreListViewType.LOADING
        }
    }
}