package com.hjiee.domain.usecase.beer.detail

import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetBeerDetailUseCase @Inject constructor(
    private val repository: BeerRepository
) {

    suspend fun execute(id: Int) {
        repository.getBeerDetail(id)

    }
}