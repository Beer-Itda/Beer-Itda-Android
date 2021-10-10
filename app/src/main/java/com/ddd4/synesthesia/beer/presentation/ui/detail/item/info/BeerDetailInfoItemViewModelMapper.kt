package com.ddd4.synesthesia.beer.presentation.ui.detail.item.info

import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemViewModel

object BeerDetailInfoItemViewModelMapper {

    fun BeerDetailItemViewModel.getInfo(): BeerDetailInfoItemViewModel {
        return BeerDetailInfoItemViewModel(
            beer = beer
        )
    }
}