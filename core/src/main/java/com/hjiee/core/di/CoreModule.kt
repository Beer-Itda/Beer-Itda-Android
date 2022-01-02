package com.hjiee.core.di

import android.app.Application
import com.hjiee.core.manager.FirebaseConfigManager
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.core.provider.StringProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideVersionManager(
        application: Application,
        sharedPreference: SharedPreferenceProvider
    ): VersionManager {
        return VersionManager(
            context = application,
            preference = sharedPreference
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseConfigManager(
        sharedPreference: SharedPreferenceProvider,
    ): FirebaseConfigManager {
        return FirebaseConfigManager(
            preference = sharedPreference
        )
    }

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPreferenceProvider {
        return SharedPreferenceProvider(context = application)
    }

    @Provides
    @Singleton
    fun provideStringResource(application: Application): StringProvider {
        return StringProvider(context = application)
    }
}