package com.hjiee.domain.usecase.login

import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class UserInfoUseCase @Inject constructor(
    private val repository: BeerRepository
) {

    suspend fun execute(): DomainEntity.User? {
        return repository.getUserInfo()
    }
}