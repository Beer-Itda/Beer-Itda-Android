package com.hjiee.domain.usecase.login

import com.hjiee.domain.repository.LoginRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    fun execute(tokenInfo : (String) -> Unit) {
        return repository.tokenInfo(tokenInfo)
    }
}