package com.hjiee.presentation.ui.main.home.main.item.parent.list

import com.hjiee.presentation.ui.main.home.main.entity.HomeActionEntity
import com.hjiee.presentation.ui.main.home.main.entity.HomeSelectEntity
import com.hjiee.presentation.ui.main.home.main.item.IHomeItemViewModel
import com.hjiee.presentation.ui.main.home.main.item.child.HomeBeerChildItemViewModel
import com.hjiee.presentation.ui.main.home.main.view.HomeBeerRecommendType
import com.hjiee.core.event.SelectActionEventNotifier

class BeerListItemViewModel(
    val type: HomeBeerRecommendType,
    val title: String,
    val beers: List<HomeBeerChildItemViewModel> = emptyList(),
    val eventNotifier: SelectActionEventNotifier
) : IHomeItemViewModel {

    fun clickTitle() {
        eventNotifier.notifySelectEvent(HomeSelectEntity.ClickTitle(type, title))
    }

    fun loadMore() {
        eventNotifier.notifyActionEvent(HomeActionEntity.LoadMore(this))
    }
}