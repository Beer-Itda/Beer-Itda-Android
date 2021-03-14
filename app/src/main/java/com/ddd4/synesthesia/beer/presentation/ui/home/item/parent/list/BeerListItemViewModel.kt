package com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.list

import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.BeerFilter
import com.ddd4.synesthesia.beer.presentation.ui.home.entity.HomeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.entity.HomeSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.item.IHomeItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.item.child.HomeBeerChildItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.view.HomeStringProvider
import com.ddd4.synesthesia.beer.util.sort.SortType

class BeerListItemViewModel(
    val sortType: SortType?,
    val type: HomeStringProvider.Code,
    val title: String,
    val filter: BeerFilter,
    val beers: List<HomeBeerChildItemViewModel>,
    val eventNotifier: SelectActionEventNotifier
) : IHomeItemViewModel {

    fun clickTitle() {
        eventNotifier.notifySelectEvent(HomeSelectEntity.ClickTitle(sortType, type, title, filter))
    }

    fun loadMore() {
        eventNotifier.notifyActionEvent(HomeActionEntity.LoadMore)
    }
}