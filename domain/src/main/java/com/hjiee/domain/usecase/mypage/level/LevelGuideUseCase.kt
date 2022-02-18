package com.hjiee.domain.usecase.mypage.level

import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class LevelGuideUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(): DomainEntity.Level? {
        return repository.getLevelGuide()
    }
}