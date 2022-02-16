package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.parent.list

import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.entity.HomeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.entity.HomeSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.IHomeItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.child.HomeBeerChildItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.view.HomeBeerRecommendType
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