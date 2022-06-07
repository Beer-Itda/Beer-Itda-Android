package com.hjiee.presentation.ui.main.home.main.item

import com.hjiee.presentation.base.recyclerview2.IViewTypeGetter
import com.hjiee.presentation.ui.main.home.main.item.parent.award.BeerAwardItemViewModel
import com.hjiee.presentation.ui.main.home.main.view.HomeViewType

interface IHomeItemViewModel : IViewTypeGetter<HomeViewType> {
    override fun getViewType(): HomeViewType {
        return when (this) {
            is BeerAwardItemViewModel -> HomeViewType.AWARD
            else -> HomeViewType.RECOMMEND
        }
    }
}