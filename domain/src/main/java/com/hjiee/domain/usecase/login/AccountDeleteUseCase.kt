package com.hjiee.domain.usecase.login

import com.hjiee.domain.repository.LoginRepository
import javax.inject.Inject

/**
 * 회원 탈퇴
 */
class AccountDeleteUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend fun execute(
        success: (() -> Unit)? = null,
        failure: (() -> Unit)? = null
    ) {
        repository.deleteAccount {
            if (it) {
                success?.invoke()
            } else {
                failure?.invoke()
            }
        }
    }
}