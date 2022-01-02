package com.hjiee.data.di

import com.hjiee.core.provider.StringProvider
import com.hjiee.data.R
import com.hjiee.data.api.BeerApi
import com.hjiee.data.api.KakaoApi
import com.hjiee.data.authentication.AuthenticationInterceptor
import com.hjiee.data.authentication.TokenAuthenticator
import com.hjiee.data.di.AuthenticatorModule.PROVIDE_NAME_AUTHENTICATOR
import com.hjiee.data.di.InterceptorModule.PROVIDE_NAME_BODY_LOGGING
import com.hjiee.data.di.InterceptorModule.PROVIDE_NAME_HEADERS
import com.hjiee.data.di.InterceptorModule.PROVIDE_NAME_HEADER_LOGGING
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val PROVIDE_NAME_BEER = "provide_beer"
    const val PROVIDE_NAME_BEER_REFRESH = "provide_beer_refresh"
    const val PROVIDE_NAME_KAKAO = "provide_kakao"
    const val PROVIDE_NAME_KAKAO_OAUTH = "provide_kakao_oauth"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named(PROVIDE_NAME_HEADERS) headers: AuthenticationInterceptor,
        @Named(PROVIDE_NAME_BODY_LOGGING) bodyLoggingInterceptor: HttpLoggingInterceptor,
        @Named(PROVIDE_NAME_HEADER_LOGGING) headerLoggingInterceptor: HttpLoggingInterceptor,
        @Named(PROVIDE_NAME_AUTHENTICATOR) authenticator: TokenAuthenticator? = null
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(headers)
            addInterceptor(bodyLoggingInterceptor)
            addInterceptor(headerLoggingInterceptor)
            authenticator?.let {
                authenticator(it)
            }
        }.build()
    }

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_BEER)
    fun provideRetrofit(
        stringProvider: StringProvider,
        @Named(PROVIDE_NAME_HEADERS) headers: AuthenticationInterceptor,
        @Named(PROVIDE_NAME_BODY_LOGGING) bodyLoggingInterceptor: HttpLoggingInterceptor,
        @Named(PROVIDE_NAME_HEADER_LOGGING) headerLoggingInterceptor: HttpLoggingInterceptor,
        @Named(PROVIDE_NAME_AUTHENTICATOR) authenticator: TokenAuthenticator
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(stringProvider.getStringRes(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                provideOkHttpClient(
                    headers = headers,
                    bodyLoggingInterceptor = bodyLoggingInterceptor,
                    headerLoggingInterceptor = headerLoggingInterceptor,
                    authenticator = authenticator
                )
            )
            .build()
    }


    @Provides
    @Singleton
    fun provideBeerService(
        @Named(PROVIDE_NAME_BEER) retrofit: Retrofit
    ): BeerApi {
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
    fun provideKakaoOAuthOkHttpClient(
        @Named(PROVIDE_NAME_BODY_LOGGING) bodyLoggingInterceptor: HttpLoggingInterceptor,
        @Named(PROVIDE_NAME_HEADER_LOGGING) headerLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                chain.run {
                    proceed(
                        request().newBuilder().apply {

                        }.build()
                    )
                }
            })
            .addInterceptor(bodyLoggingInterceptor)
            .addInterceptor(headerLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_KAKAO)
    fun provideKakaoRetrofit(
        @Named(PROVIDE_NAME_BODY_LOGGING) bodyLoggingInterceptor: HttpLoggingInterceptor,
        @Named(PROVIDE_NAME_HEADER_LOGGING) headerLoggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://kapi.kakao.com")
            .client(
                provideKakaoOAuthOkHttpClient(
                    bodyLoggingInterceptor = bodyLoggingInterceptor,
                    headerLoggingInterceptor = headerLoggingInterceptor
                )
            )
            .build()
    }


    @Provides
    @Singleton
    fun provideKakaoService(
        @Named(PROVIDE_NAME_KAKAO) retrofit: Retrofit
    ): KakaoApi {
        return retrofit.create(KakaoApi::class.java)
    }
}