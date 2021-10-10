package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.item

import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel

object MoreListModelMapper {

    fun Response?.getMapper(
        eventNotifier: SelectActionEventNotifier
    ): List<MoreListItemViewModel> {
        return this?.beers?.map {
            MoreListItemViewModel(
                beer = it.getBeerItemViewModel(
                    eventNotifier = eventNotifier
                ),
                eventNotifier = eventNotifier
            )
        } ?: kotlin.run { emptyList() }
    }

}