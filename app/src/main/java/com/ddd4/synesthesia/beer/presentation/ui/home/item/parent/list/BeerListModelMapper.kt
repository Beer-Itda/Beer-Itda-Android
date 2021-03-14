package com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.list

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.BeerFilter
import com.ddd4.synesthesia.beer.presentation.ui.home.item.child.HomeBeerChildItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.view.HomeStringProvider
import com.ddd4.synesthesia.beer.util.sort.SortType

object BeerListModelMapper {

    fun List<Beer>.getMapper(
        sortType: SortType?,
        type: HomeStringProvider.Code,
        title: String,
        filter: BeerFilter,
        eventNotifier: SelectActionEventNotifier
    ): BeerListItemViewModel {
        val list = this.map {
            HomeBeerChildItemViewModel(
                BeerItemViewModelMapper.getMapper(
                    beer = it,
                    eventNotifier = eventNotifier
                )
            )

        }
        return BeerListItemViewModel(
            sortType = sortType,
            type = type,
            title = title,
            beers = list,
            filter = filter,
            eventNotifier = eventNotifier
        )
    }

}