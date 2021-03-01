package com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.award

import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel

object BeerAwardModelMapper {

    fun getMapper(beer: BeerItemViewModel): BeerAwardItemViewModel {
        return BeerAwardItemViewModel(beer)
    }
}