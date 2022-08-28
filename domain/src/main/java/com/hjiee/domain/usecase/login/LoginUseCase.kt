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
    suspend fun execute(accessToken: String): DomainEntity.LoginInfo {
        val loginInfo = repository.login(accessToken)
        preference.setValue(Consts.IS_FIRST_SIGNUP_USER, loginInfo.isFirstSignupUser)
        if (loginInfo.accessToken.isNotEmpty()) {
            preference.setValue(Consts.ACCESS_TOKEN, loginInfo.accessToken)
        }
        if (loginInfo.refreshToken.isNotEmpty()) {
            preference.setValue(Consts.REFRESH_TOKEN, loginInfo.refreshToken)
        }
        return loginInfo
    }
}