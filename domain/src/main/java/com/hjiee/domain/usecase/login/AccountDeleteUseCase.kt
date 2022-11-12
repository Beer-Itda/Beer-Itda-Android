package com.hjiee.domain.usecase.login

import com.hjiee.core.Consts
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.domain.repository.LoginRepository
import javax.inject.Inject

/**
 * 회원 탈퇴
 */
class AccountDeleteUseCase @Inject constructor(
    private val preferenceProvider: SharedPreferenceProvider,
    private val repository: LoginRepository
) {

    suspend fun execute(
        success: (() -> Unit)? = null,
        failure: (() -> Unit)? = null
    ) {
        repository.deleteAccount {
            if (it) {
                preferenceProvider.remove(Consts.ACCESS_TOKEN)
                preferenceProvider.remove(Consts.REFRESH_TOKEN)
                success?.invoke()
            } else {
                failure?.invoke()
            }
        }
    }
}