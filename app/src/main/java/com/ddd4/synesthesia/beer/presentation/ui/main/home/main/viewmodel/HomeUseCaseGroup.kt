package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.viewmodel

import com.hjiee.domain.usecase.beer.GetAwardBeerUseCase
import com.hjiee.domain.usecase.beer.GetSelectedAromaBeerUseCase
import com.hjiee.domain.usecase.beer.GetSelectedStyleBeerUseCase
import javax.inject.Inject

class HomeUseCaseGroup @Inject constructor(
    val getAwardBeerUseCase: GetAwardBeerUseCase,
    val getSelectedStyleBeerUseCase: GetSelectedStyleBeerUseCase,
    val getSelectedAromaBeerUseCase: GetSelectedAromaBeerUseCase
)