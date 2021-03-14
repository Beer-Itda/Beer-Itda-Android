package com.ddd4.synesthesia.beer.presentation.ui.like.item

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper

object HomeLikeListModelMapper {

    fun getMapper(
        beers: List<Beer>,
        eventNotifier: SelectActionEventNotifier
    ): List<HomeLikeListItemViewModel> {
        return beers.map {
            HomeLikeListItemViewModel(
                beer = BeerItemViewModelMapper.getMapper(
                    beer = it,
                    eventNotifier = eventNotifier
                ),
                eventNotifier = eventNotifier
            )

        }
    }

}