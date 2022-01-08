package com.hjiee.domain.usecase.beer

import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class PostFavoriteUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {

    suspend fun execute(beerId: Int) {
        beerRepository.postFavorite(beerId = beerId)
    }
}