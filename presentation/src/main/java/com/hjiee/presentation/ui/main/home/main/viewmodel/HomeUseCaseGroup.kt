package com.hjiee.presentation.ui.main.home.main.viewmodel

import com.hjiee.domain.usecase.beer.*
import javax.inject.Inject

class HomeUseCaseGroup @Inject constructor(
    val awardBeer: GetAwardBeerUseCase,
    val getRandomRecommendBeer: GetRandomRecommendUseCase,
    val getSelectedStyleBeerUseCase: GetSelectedStyleBeerUseCase,
    val getSelectedAromaBeerUseCase: GetSelectedAromaBeerUseCase,
    val favorite : PostFavoriteUseCase
)