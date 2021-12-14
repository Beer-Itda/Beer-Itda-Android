package com.hjiee.domain.usecase.login

import com.hjiee.domain.repository.LoginRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend fun execute(
        success: (() -> Unit)? = null,
        failure: (() -> Unit)? = null
    ) {
        repository.logout {
            if (it) {
                success?.invoke()
            } else {
                failure?.invoke()
            }
        }
    }
}