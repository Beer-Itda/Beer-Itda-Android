package com.hjiee.data.di

import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.core.provider.StringProvider
import com.hjiee.data.R
import com.hjiee.data.TokenAuthenticator
import com.hjiee.data.api.BeerApi
import com.hjiee.data.api.KakaoApi
import com.hjiee.data.di.NetworkModule.PROVIDE_NAME_BEER_REFRESH
import com.hjiee.data.repository.LoginRepositoryImpl
import com.hjiee.domain.usecase.login.RefreshTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AuthenticatorModule {

    const val PROVIDE_NAME_AUTHENTICATOR = "authenticator"

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_AUTHENTICATOR)
    fun provideAuthenticator(
        preference: SharedPreferenceProvider,
        @Named(PROVIDE_NAME_BEER_REFRESH) beerApi: BeerApi,
        kakaoApi: KakaoApi
    ): TokenAuthenticator {
        return TokenAuthenticator(
            useCase = RefreshTokenUseCase(
                repository = LoginRepositoryImpl(
                    beerApi = beerApi,
                    kakaoApi = kakaoApi
                )
            ),
            preference = preference
        )
    }

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_BEER_REFRESH)
    fun provideRetrofit(
        stringProvider: StringProvider,
        @Named(InterceptorModule.PROVIDE_NAME_HEADERS) headers: Interceptor,
        @Named(InterceptorModule.PROVIDE_NAME_BODY_LOGGING) bodyLoggingInterceptor: HttpLoggingInterceptor,
        @Named(InterceptorModule.PROVIDE_NAME_HEADER_LOGGING) headerLoggingInterceptor: HttpLoggingInterceptor,
    ): BeerApi {
        return Retrofit.Builder()
            .baseUrl(stringProvider.getStringRes(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                NetworkModule.provideOkHttpClient(
                    headers = headers,
                    bodyLoggingInterceptor = bodyLoggingInterceptor,
                    headerLoggingInterceptor = headerLoggingInterceptor,
                )
            )
            .build()
            .create(BeerApi::class.java)
    }
}