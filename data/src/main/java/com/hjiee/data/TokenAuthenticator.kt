package com.hjiee.data

import com.hjiee.core.Consts.REFRESH_TOKEN
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.data.di.InterceptorModule.EXTRA_HEADER_NAME_AUTHORIZATION
import com.hjiee.data.di.InterceptorModule.EXTRA_HEADER_NAME_BEARER
import com.hjiee.domain.usecase.login.RefreshTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val useCase: RefreshTokenUseCase,
    private val preference: SharedPreferenceProvider
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            val refreshToken = preference.getPreferenceString(REFRESH_TOKEN).orEmpty()

            val getNewDeviceToken =
                GlobalScope.async(Dispatchers.Default) { getNewDeviceToken(refreshToken) }
            val token = runBlocking { getNewDeviceToken.await().orEmpty() }
            return getRequest(response, token)
        }
        return null
    }

    private suspend inline fun getNewDeviceToken(token: String): String? {
        return GlobalScope.async(Dispatchers.Default) { useCase.execute(token) }.await().accessToken
    }


    private fun getRequest(response: Response, accessToken: String): Request {
        return response.request.newBuilder()
            .removeHeader(EXTRA_HEADER_NAME_AUTHORIZATION)
            .addHeader(EXTRA_HEADER_NAME_AUTHORIZATION, "$EXTRA_HEADER_NAME_BEARER $accessToken")
            .build()
    }
}