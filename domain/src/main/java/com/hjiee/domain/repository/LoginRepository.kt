package com.hjiee.domain.repository

import com.hjiee.domain.entity.DomainEntity.TokenInfo
import com.hjiee.domain.entity.DomainEntity.Token
import com.hjiee.domain.entity.DomainEntity.User

interface LoginRepository {
//    fun tokenInfo(tokenInfo: (Token?) -> Unit)
//
//    suspend fun accessToken(
//        code: String?,
//        accessToken: ((Token?) -> Unit)? = null
//    ): Token
//
    suspend fun login(token: String): TokenInfo
//    fun logout(isSuccess: (Boolean) -> Unit)
//    fun unlink(isSuccess: (Boolean) -> Unit)
//    fun me(userInfo: (User?) -> Unit)
}