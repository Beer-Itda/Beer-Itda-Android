package com.ddd4.synesthesia.beer.di.module

import android.app.Application
import android.content.Context
import com.ddd4.synesthesia.beer.util.AppConfig
import com.ddd4.synesthesia.beer.util.provider.SharedPreferenceProvider
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.FilterImpl
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.FilterSetting
import com.ddd4.synesthesia.beer.util.provider.StringProvider
import com.ddd4.synesthesia.beer.util.sort.SortImpl
import com.ddd4.synesthesia.beer.util.sort.SortSetting
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

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

    @Provides
    @Singleton
    fun provideAppConfig(application: Application,sharedPreference : SharedPreferenceProvider): AppConfig {
        return AppConfig(application,sharedPreference)
    }

    @Provides
    @Singleton
    fun provideSortSettings(
        preference: SharedPreferenceProvider,
        @ApplicationContext context: Context
    ): SortSetting = SortImpl(preference, context)

    @Provides
    @Singleton
    fun provideFilterSettings(
        preference: SharedPreferenceProvider,
        @ApplicationContext context: Context
    ): FilterSetting = FilterImpl(preference, context)

}