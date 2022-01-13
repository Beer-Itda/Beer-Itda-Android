package com.hjiee.domain.usecase.mypage

import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class MyFavoriteUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(): DomainEntity.Beers? {
        return repository.getMyFavorite()?.data
    }

}