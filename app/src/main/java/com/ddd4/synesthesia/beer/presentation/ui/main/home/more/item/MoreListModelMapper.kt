package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.item

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper

object MoreListModelMapper {

    fun getMapper(
        beers: List<Beer>,
        eventNotifier: SelectActionEventNotifier
    ): List<MoreListItemViewModel> {
        return beers.map {
            MoreListItemViewModel(
                beer = BeerItemViewModelMapper.getMapper(
                    beer = it,
                    eventNotifier = eventNotifier
                ),
                eventNotifier = eventNotifier
            )

        }
    }

}