package com.hjiee.domain.usecase.beer

import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetSelectedStyleBeerUseCase @Inject constructor(
    private val repository: BeerRepository
) {
}