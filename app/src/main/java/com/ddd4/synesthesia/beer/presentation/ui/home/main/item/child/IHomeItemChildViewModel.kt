package com.ddd4.synesthesia.beer.presentation.ui.home.main.item.child

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter
import com.ddd4.synesthesia.beer.presentation.ui.home.main.view.HomeChildViewType

interface IHomeItemChildViewModel : IViewTypeGetter<HomeChildViewType> {
    override fun getViewType(): HomeChildViewType {
        return when (this) {
            is HomeBeerChildItemViewModel -> HomeChildViewType.CHOICE
            else -> HomeChildViewType.CHOICE
        }
    }
}