package com.hjiee.domain.repository

import com.hjiee.domain.entity.DomainEntity.TokenInfo
import com.hjiee.domain.entity.DomainEntity.Token
import com.hjiee.domain.entity.DomainEntity.User

interface LoginRepository {
    //    fun tokenInfo(tokenInfo: (Token?) -> Unit)
//
    suspend fun tokenInfo(code: String): TokenInfo

    //
    suspend fun login(accessToken: String): TokenInfo
//    fun logout(isSuccess: (Boolean) -> Unit)
//    fun unlink(isSuccess: (Boolean) -> Unit)
//    fun me(userInfo: (User?) -> Unit)
}