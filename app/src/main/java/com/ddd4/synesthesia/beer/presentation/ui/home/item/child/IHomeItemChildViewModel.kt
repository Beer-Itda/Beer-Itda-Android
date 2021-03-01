package com.ddd4.synesthesia.beer.presentation.ui.home.item.child

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.view.HomeChildViewType

interface IHomeItemChildViewModel : IViewTypeGetter<HomeChildViewType> {
    override fun getViewType(): HomeChildViewType {
        return when(this) {
            is BeerItemViewModel -> HomeChildViewType.CHOICE
            else -> HomeChildViewType.CHOICE
        }
    }
}