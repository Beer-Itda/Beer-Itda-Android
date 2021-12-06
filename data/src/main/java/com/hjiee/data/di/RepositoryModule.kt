package com.hjiee.data.di

import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.data.repository.BeerRepositoryImpl
import com.hjiee.data.repository.LoginRepositoryImpl
import com.hjiee.data.api.BeerApi
import com.hjiee.data.api.KakaoApi
import com.hjiee.data.api.KakaoAuthApi
import com.hjiee.domain.repository.BeerRepository
import com.hjiee.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        kakaoApi: KakaoApi,
        beerApi: BeerApi,
        kakaoAuthApi: KakaoAuthApi,
        preference: SharedPreferenceProvider
    ): LoginRepository {
        return LoginRepositoryImpl(kakaoApi,beerApi, kakaoAuthApi, preference)
    }

    @Provides
    @Singleton
    fun provideBeerRepository(beerApi: BeerApi): BeerRepository {
        return BeerRepositoryImpl(beerApi)
    }
}