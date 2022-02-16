package com.hjiee.domain.usecase.mypage

import com.hjiee.core.ext.orZero
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.ApiServiceConstants.DEFAULT_FIRST_PAGE
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class MyFavoriteUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(next: Int? = DEFAULT_FIRST_PAGE): DomainEntity.PageResult<DomainEntity.Beer> {
        return repository.getMyFavorite(next.orZero())
    }

}