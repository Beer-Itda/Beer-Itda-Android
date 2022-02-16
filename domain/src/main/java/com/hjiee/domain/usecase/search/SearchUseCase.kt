package com.hjiee.domain.usecase.search

import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(word: String, page: Int, size: Int = 20): DomainEntity.PageResult<DomainEntity.Beer> {
        return repository.search(
            word = word,
            page = page,
            size = size
        )
    }
}