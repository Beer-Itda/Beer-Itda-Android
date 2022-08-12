package com.hjiee.data.repository

import com.hjiee.data.api.BeerApi
import com.hjiee.data.api.KakaoApi
import com.hjiee.data.mapper.toTokenInfo
import com.hjiee.domain.entity.DomainEntity.TokenInfo
import com.hjiee.domain.repository.LoginRepository
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.user.UserApiClient

class LoginRepositoryImpl(
    private val kakaoApi: KakaoApi,
    private val beerApi: BeerApi
) : LoginRepository {

    override suspend fun login(token: String): TokenInfo {
        return beerApi.kakaoLogin(token = token).toTokenInfo()
    }

    override suspend fun logout(isSuccess: (Boolean) -> Unit) {
        UserApiClient.instance.logout { error ->
            isSuccess(error == null)
        }
    }

    override suspend fun refreshToken(refreshToken: String): TokenInfo {
        return beerApi.refreshToken(refreshToken).toTokenInfo()
    }

    override fun tokenInfo(tokenInfo: (String) -> Unit) {
        UserApiClient.instance.accessTokenInfo { token, error ->
            if (error != null) {
                tokenInfo.invoke("")
            } else if (token != null) {
                tokenInfo.invoke(TokenManagerProvider.instance.manager.getToken()?.accessToken.orEmpty())
            }
        }
    }

    override suspend fun deleteAccount(isSuccess: (Boolean) -> Unit) {
        val result = beerApi.accountWithdraw()
        if (result.isSuccessful) {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    isSuccess(false)
                } else {
                    isSuccess(true)
                }
            }
        } else {
            isSuccess(false)
        }
    }
}