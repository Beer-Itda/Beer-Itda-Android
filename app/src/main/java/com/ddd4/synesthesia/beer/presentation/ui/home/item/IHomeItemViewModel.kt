package com.ddd4.synesthesia.beer.presentation.ui.home.item

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter
import com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.award.BeerAwardItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.view.HomeViewType

interface IHomeItemViewModel : IViewTypeGetter<HomeViewType> {
    override fun getViewType(): HomeViewType {
        return when (this) {
            is BeerAwardItemViewModel -> HomeViewType.AWARD
            else -> HomeViewType.RECOMMEND
        }
    }
}