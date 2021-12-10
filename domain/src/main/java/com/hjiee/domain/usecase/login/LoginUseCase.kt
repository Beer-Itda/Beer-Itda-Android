package com.hjiee.domain.usecase.login

import com.hjiee.core.Consts
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val preference: SharedPreferenceProvider
) {
    suspend fun execute(accessToken: String): DomainEntity.TokenInfo {
        val tokenInfo = repository.login(accessToken)
        preference.setValue(Consts.ACCESS_TOKEN, tokenInfo.accessToken)
        preference.setValue(Consts.REFRESH_TOKEN, tokenInfo.refreshToken)
        return tokenInfo
    }
}