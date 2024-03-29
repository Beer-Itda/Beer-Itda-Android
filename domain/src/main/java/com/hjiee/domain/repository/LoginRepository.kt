package com.hjiee.domain.repository

import com.hjiee.domain.entity.DomainEntity.LoginInfo

interface LoginRepository {
    /**
     * 카카오에서 token 정보를 가져온다
     */
    fun tokenInfo(tokenInfo : (String) -> Unit)

    /**
     * 로그인
     */
    suspend fun login(accessToken: String): LoginInfo

    /**
     * 로그아웃
     */
    suspend fun logout(isSuccess : (Boolean) -> Unit)

    /**
     * refresh
     */
    suspend fun refreshToken(refreshToken: String): LoginInfo

    /**
     * 회원 탈퇴
     */
    suspend fun deleteAccount(isSuccess: (Boolean) -> Unit)
//    fun unlink(isSuccess: (Boolean) -> Unit)
//    fun me(userInfo: (User?) -> Unit)
}