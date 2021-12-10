package com.hjiee.data.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.hjiee.core.AppInfo
import com.hjiee.core.BuildConfig
import com.hjiee.core.Consts
import com.hjiee.core.Consts.ACCESS_TOKEN
import com.hjiee.core.provider.SharedPreferenceProvider.Companion.SHARED_PRIVATE_KEY
import com.hjiee.data.api.BeerApi
import com.hjiee.data.api.KakaoApi
import com.hjiee.data.api.KakaoAuthApi
import com.hjiee.domain.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application, appInfo: AppInfo): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor.invoke {
                it.run {
                    val accessToken =
                        application.getSharedPreferences(SHARED_PRIVATE_KEY, MODE_PRIVATE)
                            .getString(ACCESS_TOKEN, "").orEmpty()
                    Timber.tag("tokenInfo").w(accessToken)

                    val request = request().newBuilder()
                        .addHeader("Platform", Consts.PLATFORM)
                        .addHeader("AppVersion", appInfo.version)
                        .addHeader("Authorization", "Bearer $accessToken")
                        .build()
                    proceed(request)
                }
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }

    @Provides
    @Singleton
    @Named("beer")
    fun provideRetrofit(application: Application, appInfo: AppInfo): Retrofit {
        return Retrofit.Builder()
            .baseUrl(application.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient(application, appInfo))
            .build()
    }

    @Provides
    @Singleton
    fun provideBeerService(@Named("beer") retrofit: Retrofit): BeerApi {
        return retrofit.create(BeerApi::class.java)
    }

    /**
     * 카카오 API
     * 리다이렉트 access token
     * login
     * logout
     */
    @Provides
    @Singleton
    fun provideKakaoOAuthOkHttpClient(application: Application): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor.invoke {
                it.run {
                    Timber.tag("tokenInfo").w("test")
                    val request = request().newBuilder()
                    val accessToken =
                        application.getSharedPreferences(SHARED_PRIVATE_KEY, MODE_PRIVATE)
                            .getString(ACCESS_TOKEN, "").orEmpty()
                    Timber.tag("tokenInfo").w(accessToken)
                    request.addHeader("Authorization", "Bearer $accessToken")
                    proceed(request.build())
                }
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }

    @Provides
    @Singleton
    @Named("kakaoAuth")
    fun provideKakaoAuthRetrofit(application: Application): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://kauth.kakao.com")
            .client(provideKakaoOAuthOkHttpClient(application))
            .build()
    }

    @Provides
    @Singleton
    @Named("kakao")
    fun provideKakaoRetrofit(application: Application): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://kapi.kakao.com")
            .client(provideKakaoOAuthOkHttpClient(application))
            .build()
    }

    @Provides
    @Singleton
    fun provideKakaoAuthService(@Named("kakaoAuth") retrofit: Retrofit): KakaoAuthApi {
        return retrofit.create(KakaoAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKakaoService(@Named("kakao") retrofit: Retrofit): KakaoApi {
        return retrofit.create(KakaoApi::class.java)
    }
}