package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.parent.award

import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.child.HomeBeerChildItemViewModel

object BeerAwardModelMapper {

    fun getMapper(beer: BeerItemViewModel): BeerAwardItemViewModel {
        return BeerAwardItemViewModel(HomeBeerChildItemViewModel(beer))
    }
}