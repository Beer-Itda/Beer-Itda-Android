package com.hjiee.domain.usecase.beer

import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetBeerUseCase @Inject constructor(
    private val repository: BeerRepository
) {

    suspend operator fun invoke(id: Int) {
        repository.getBeerDetail(id).let {

        }
    }
}