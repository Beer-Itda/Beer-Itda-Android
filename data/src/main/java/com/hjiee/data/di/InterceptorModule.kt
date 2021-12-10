package com.hjiee.data.di

import com.hjiee.core.BuildConfig
import com.hjiee.core.Consts
import com.hjiee.core.Consts.ACCESS_TOKEN
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object InterceptorModule {

    const val EXTRA_HEADER_NAME_PLATFORM = "Platform"
    const val EXTRA_HEADER_NAME_APP_VERSION = "AppVersion"
    const val EXTRA_HEADER_NAME_AUTHORIZATION = "Authorization"
    const val EXTRA_HEADER_NAME_BEARER = "Bearer"
    const val PROVIDE_NAME_HEADERS = "provide_headers"
    const val PROVIDE_NAME_BODY_LOGGING = "provide_body_logging_interceptor"
    const val PROVIDE_NAME_HEADER_LOGGING = "provide_header_logging_interceptor"

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_HEADERS)
    fun provideHeaders(
        preference: SharedPreferenceProvider,
        versionManager: VersionManager
    ): Interceptor {
        val accessToken = preference.getPreferenceString(ACCESS_TOKEN)
        val version = versionManager.version
        return Interceptor { chain ->
            chain.run {
                proceed(
                    request().newBuilder().apply {
                        addHeader(EXTRA_HEADER_NAME_PLATFORM, Consts.PLATFORM)
                        addHeader(EXTRA_HEADER_NAME_APP_VERSION, version)
                        addHeader(EXTRA_HEADER_NAME_AUTHORIZATION, "$EXTRA_HEADER_NAME_BEARER $accessToken")
                    }.build()
                )
            }
        }
    }

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_BODY_LOGGING)
    fun provideBodyHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_HEADER_LOGGING)
    fun provideHeaderHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.HEADERS
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}