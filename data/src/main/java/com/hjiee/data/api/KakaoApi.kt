package com.hjiee.data.api

import com.hjiee.data.response.LogoutResponse
import retrofit2.http.POST

interface KakaoApi {

    /**
     * 로그아웃
     */
    @POST("v1/user/logout")
    suspend fun logout(): LogoutResponse

    /**
     * 연결끊기
     */
    @POST("v1/user/unlink")
    suspend fun unlike(): LogoutResponse
}