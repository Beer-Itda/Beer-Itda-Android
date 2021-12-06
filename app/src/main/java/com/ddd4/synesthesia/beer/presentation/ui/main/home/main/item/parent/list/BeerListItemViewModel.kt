package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.parent.list

import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.hjiee.core.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.entity.HomeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.entity.HomeSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.IHomeItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.child.HomeBeerChildItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.view.HomeStringProvider
import com.ddd4.synesthesia.beer.util.sort.SortType

class BeerListItemViewModel(
    val type: HomeStringProvider.Code,
    val title: String,
    val beers: List<HomeBeerChildItemViewModel> = emptyList(),
    val eventNotifier: SelectActionEventNotifier
) : IHomeItemViewModel {

    fun clickTitle() {
        eventNotifier.notifySelectEvent(HomeSelectEntity.ClickTitle(type, title))
    }

    fun loadMore() {
        eventNotifier.notifyActionEvent(HomeActionEntity.LoadMore)
    }
}