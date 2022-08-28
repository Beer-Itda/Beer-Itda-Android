package com.hjiee.data.authentication

import com.hjiee.core.Consts
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.data.di.InterceptorModule
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
    private val preference: SharedPreferenceProvider,
    private val versionManager: VersionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = preference.getPreferenceString(Consts.ACCESS_TOKEN)

        return chain.run {
            proceed(
                request().newBuilder().apply {
                    addHeader(InterceptorModule.EXTRA_HEADER_NAME_PLATFORM, Consts.PLATFORM)
                    addHeader(InterceptorModule.EXTRA_HEADER_NAME_APP_VERSION, versionManager.version)
                    if(accessToken.isNullOrEmpty().not()) {
                        addHeader(InterceptorModule.EXTRA_HEADER_NAME_AUTHORIZATION, "${InterceptorModule.EXTRA_HEADER_NAME_BEARER} $accessToken")
                    }
                }.build()
            )
        }
    }
}