package com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.list

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.base.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper
import com.ddd4.synesthesia.beer.presentation.ui.home.view.HomeStringProvider

object BeerListModelMapper {

    fun List<Beer>.getMapper(
        type : HomeStringProvider.Code,
        title: String,
        eventNotifier: SelectActionEventNotifier
    ): BeerListItemViewModel {
        val list = this.map {
            BeerItemViewModelMapper.getMapper(
                beer = it,
                eventNotifier = eventNotifier
            )
        }
        return BeerListItemViewModel(
            type = type,
            title = title,
            beer = list,
            eventNotifier = eventNotifier
        )
    }

}