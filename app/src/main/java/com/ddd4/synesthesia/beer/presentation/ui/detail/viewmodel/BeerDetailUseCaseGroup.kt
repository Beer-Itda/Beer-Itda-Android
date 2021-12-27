package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import com.hjiee.domain.usecase.beer.detail.GetBeerDetailUseCase
import javax.inject.Inject

class BeerDetailUseCaseGroup @Inject constructor(
    val getBeerDetail: GetBeerDetailUseCase
) {
}