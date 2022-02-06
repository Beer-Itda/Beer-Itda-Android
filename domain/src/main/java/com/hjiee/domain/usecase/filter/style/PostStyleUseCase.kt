package com.hjiee.domain.usecase.filter.style

import com.hjiee.domain.entity.request.RequestSelectedStyle
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class PostStyleUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {

    suspend fun execute(
        styleIdList: RequestSelectedStyle
    ) {
        beerRepository.postSelectedStyle(
            styleIdList = styleIdList
        )
    }
}