package com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.list

import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.entity.HomeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.entity.HomeSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.item.IHomeItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.view.HomeStringProvider

class BeerListItemViewModel(
    val type : HomeStringProvider.Code,
    val title : String,
    val beer : List<BeerItemViewModel>,
    val eventNotifier: SelectActionEventNotifier
) : IHomeItemViewModel {

    fun clickTitle() {
        eventNotifier.notifySelectEvent(HomeSelectEntity.ClickTitle(type))
    }

    fun loadMore() {
        eventNotifier.notifyActionEvent(HomeActionEntity.LoadMore)
    }
}