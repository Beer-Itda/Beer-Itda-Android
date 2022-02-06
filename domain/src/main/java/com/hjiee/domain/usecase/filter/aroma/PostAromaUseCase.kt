package com.hjiee.domain.usecase.filter.aroma

import com.hjiee.domain.entity.request.RequestSelectedAroma
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class PostAromaUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {

    suspend fun execute(
        aromaIdList: RequestSelectedAroma
    ) {
        beerRepository.postSelectedAroma(
            aromaIdList = aromaIdList
        )
    }
}