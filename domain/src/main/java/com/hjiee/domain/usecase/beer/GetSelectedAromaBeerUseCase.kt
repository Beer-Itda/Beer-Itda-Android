package com.hjiee.domain.usecase.beer

import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetSelectedAromaBeerUseCase @Inject constructor(
    private val repository: BeerRepository
) {

    suspend fun execute(
        eventNotifier: SelectActionEventNotifier
    ): List<DomainEntity.Beer> {
        return repository.getAromaBeer()?.data?.beers.orEmpty()
    }
}