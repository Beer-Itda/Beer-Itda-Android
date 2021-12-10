package com.hjiee.domain.usecase.login

import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.LoginRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend fun execute(code: String): DomainEntity.TokenInfo {
        return repository.tokenInfo(code)
    }
}