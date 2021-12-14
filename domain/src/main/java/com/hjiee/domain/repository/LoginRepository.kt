package com.hjiee.domain.repository

import com.hjiee.domain.entity.DomainEntity.TokenInfo
import com.hjiee.domain.entity.DomainEntity.Token
import com.hjiee.domain.entity.DomainEntity.User

interface LoginRepository {
    /**
     * 카카오에서 token 정보를 가져온다
     */
    suspend fun tokenInfo(tokenInfo : (String) -> Unit)

    /**
     * 로그인
     */
    suspend fun login(accessToken: String): TokenInfo

    /**
     * 로그아웃
     */
    suspend fun logout(isSuccess : (Boolean) -> Unit)

    /**
     * refresh
     */
    suspend fun refreshToken(refreshToken: String): TokenInfo
//    fun unlink(isSuccess: (Boolean) -> Unit)
//    fun me(userInfo: (User?) -> Unit)
}