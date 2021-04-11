package com.ddd4.synesthesia.beer.presentation.ui.home.main.item.parent.list

import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.BeerFilter
import com.ddd4.synesthesia.beer.presentation.ui.home.main.entity.HomeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.main.entity.HomeSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.main.item.IHomeItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.main.item.child.HomeBeerChildItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.main.view.HomeStringProvider
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