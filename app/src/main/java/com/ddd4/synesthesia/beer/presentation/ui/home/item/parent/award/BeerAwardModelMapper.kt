package com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.award

import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.item.child.HomeBeerChildItemViewModel

object BeerAwardModelMapper {

    fun getMapper(beer: BeerItemViewModel): BeerAwardItemViewModel {
        return BeerAwardItemViewModel(HomeBeerChildItemViewModel(beer))
    }
}