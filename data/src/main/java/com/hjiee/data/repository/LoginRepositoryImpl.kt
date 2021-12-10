package com.hjiee.data.repository

import com.hjiee.data.api.BeerApi
import com.hjiee.data.api.KakaoApi
import com.hjiee.data.api.KakaoAuthApi
import com.hjiee.data.mapper.toTokenInfo
import com.hjiee.domain.entity.DomainEntity.TokenInfo
import com.hjiee.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val kakaoApi: KakaoApi,
    private val beerApi: BeerApi,
    private val kakaoAuthApi: KakaoAuthApi
) : LoginRepository {

    override suspend fun tokenInfo(code: String): TokenInfo {
        return kakaoAuthApi.accessToken(code = code).toTokenInfo()
    }

    override suspend fun login(token: String): TokenInfo {
        return beerApi.kakaoLogin(token = token).toTokenInfo()
    }

    //    override fun tokenInfo(tokenInfo: (OAuthToken?) -> Unit) {
//        UserApiClient.instance.accessTokenInfo { token, error ->
//            if (error != null) {
//                tokenInfo.invoke(null)
//                preference.remove("token")
//            } else if (token != null) {
//                preference.setPreference(
//                    "token",
//                    TokenManagerProvider.instance.manager.getToken()?.accessToken
//                )
//                tokenInfo.invoke(TokenManagerProvider.instance.manager.getToken())
//            }
//        }
//    }
//
//    override fun login(userInfo: (User?, Throwable?) -> Unit) {
//        UserApiClient.instance.me { user, error ->
//            if (error != null) {
//                userInfo.invoke(null, error)
//            } else if (user != null) {
//                userInfo.invoke(user, null)
//            }
//        }
//    }
//
//
//    override fun logout(isSuccess: (Boolean) -> Unit) {
//        UserApiClient.instance.logout { error ->
//            if (error != null) {
//                isSuccess.invoke(false)
//            } else {
//                isSuccess.invoke(true)
//            }
//        }
//    }
//
//    override fun unlink(isSuccess: (Boolean) -> Unit) {
//        UserApiClient.instance.unlink { error ->
//            if (error != null) {
//                isSuccess.invoke(false)
//            } else {
//                isSuccess.invoke(true)
//            }
//        }
//    }
//
//    override fun me(userInfo: (User?) -> Unit) {
//        UserApiClient.instance.me { user, error ->
//            userInfo.invoke(user)
//        }
//
//    }
//
//    override suspend fun accessToken(
//        code: String?,
//        accessToken: ((TokenResponse?) -> Unit)?
//    ): TokenResponse {
//        val token = kakaoAuthApi.accessToken(code = code)
//        accessToken?.invoke(token)
//        return token
//    }
}