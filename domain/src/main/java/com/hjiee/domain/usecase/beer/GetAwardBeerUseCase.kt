package com.hjiee.domain.usecase.beer

import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetAwardBeerUseCase @Inject constructor(
    private val repository: BeerRepository
){
    suspend fun execute() {

    }
}