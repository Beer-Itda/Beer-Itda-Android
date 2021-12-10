package com.hjiee.core.di

import android.app.Application
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.core.provider.StringProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideAppInfo(
        application: Application,
        sharedPreference: SharedPreferenceProvider
    ): VersionManager {
        return VersionManager(application, sharedPreference)
    }

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPreferenceProvider {
        return SharedPreferenceProvider(application)
    }

    @Provides
    @Singleton
    fun provideStringResource(application: Application): StringProvider {
        return StringProvider(application)
    }
}