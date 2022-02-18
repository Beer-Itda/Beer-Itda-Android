package com.hjiee.domain.usecase.filter.style

import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetStyleUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(): List<DomainEntity.StyleLargeCategory> {
        return repository.getStyleInfo()
    }
}