package com.hjiee.data.di

import com.hjiee.core.BuildConfig
import com.hjiee.core.Consts
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

    const val PROVIDE_NAME_HEADERS = "provide_headers"
    const val PROVIDE_NAME_BODY_LOGGING = "provide_body_logging_interceptor"
    const val PROVIDE_NAME_HEADER_LOGGING = "provide_header_logging_interceptor"

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_HEADERS)
    fun provideHeaders(): Interceptor {
        val accessToken = ""
        val version = ""
        return Interceptor { chain ->
            chain.run {
                proceed(
                    request().newBuilder().apply {
                        addHeader("Platform", Consts.PLATFORM)
                        addHeader("AppVersion", version)
                        addHeader("Authorization", "Bearer $accessToken")
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