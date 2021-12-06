package com.hjiee.domain.usecase.sns

import com.hjiee.domain.repository.LoginRepository
import javax.inject.Inject

class KakaoUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    fun tokenInfo() {

    }

    fun login() {

    }


    fun logout(isSuccess: (Boolean) -> Unit) {

    }

    fun unlink(isSuccess: (Boolean) -> Unit) {

    }

    fun me() {

    }

//    suspend fun accessToken(
//        code: String?,
//        accessToken: ((TokenResponse?) -> Unit)?
//    ): TokenResponse {
//
//    }
}