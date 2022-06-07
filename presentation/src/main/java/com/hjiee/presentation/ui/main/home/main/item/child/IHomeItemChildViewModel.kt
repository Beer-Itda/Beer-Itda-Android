package com.hjiee.presentation.ui.main.home.main.item.child

import com.hjiee.presentation.base.recyclerview2.IViewTypeGetter
import com.hjiee.presentation.ui.main.home.main.view.HomeChildViewType

interface IHomeItemChildViewModel : IViewTypeGetter<HomeChildViewType> {
    override fun getViewType(): HomeChildViewType {
        return when (this) {
            is HomeBeerChildItemViewModel -> HomeChildViewType.CHOICE
            else -> HomeChildViewType.CHOICE
        }
    }
}