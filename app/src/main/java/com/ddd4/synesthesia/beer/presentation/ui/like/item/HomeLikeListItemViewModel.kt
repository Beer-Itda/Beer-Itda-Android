package com.ddd4.synesthesia.beer.presentation.ui.like.item

import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel

class HomeLikeListItemViewModel(
    val beer: BeerItemViewModel,
    val eventNotifier: SelectActionEventNotifier
) : IHomeLikeViewModel {

}