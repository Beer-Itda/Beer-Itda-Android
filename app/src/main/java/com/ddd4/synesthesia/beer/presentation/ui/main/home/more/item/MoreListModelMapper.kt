package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.item

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel

object MoreListModelMapper {

    fun getMapper(
        beers: List<Beer>,
        eventNotifier: SelectActionEventNotifier
    ): List<MoreListItemViewModel> {
        return beers.map {
            MoreListItemViewModel(
                beer = it.getBeerItemViewModel(
                    eventNotifier = eventNotifier
                ),
                eventNotifier = eventNotifier
            )

        }
    }

}