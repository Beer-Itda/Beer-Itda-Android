package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.item

import com.hjiee.core.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel

class MoreListItemViewModel(
    val beer: BeerItemViewModel,
    val eventNotifier: SelectActionEventNotifier
) : IMoreListViewModel {

}