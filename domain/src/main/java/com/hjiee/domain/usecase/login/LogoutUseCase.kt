package com.hjiee.domain.usecase.login

import com.hjiee.domain.repository.LoginRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    fun execute() {

    }
}