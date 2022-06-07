package com.hjiee.presentation.di

import android.content.Context
import com.hjiee.presentation.ui.common.filter.AromaProvider
import com.hjiee.presentation.ui.common.filter.IFilterProvider
import com.hjiee.presentation.ui.common.filter.StyleProvider
import com.hjiee.presentation.ui.filter.aroma.item.small.AromaItemViewModel
import com.hjiee.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.hjiee.presentation.util.sort.SortImpl
import com.hjiee.presentation.util.sort.SortSetting
import com.hjiee.core.provider.SharedPreferenceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(
        @ApplicationContext context: Context
    ): Context = context

    @Provides
    @Singleton
    fun provideSortSettings(
        preference: SharedPreferenceProvider,
        @ApplicationContext context: Context
    ): SortSetting = SortImpl(preference, context)

    @Provides
    @Singleton
    fun provideFilterStyle(
        preference: SharedPreferenceProvider
    ): IFilterProvider<Any, List<StyleSmallItemViewModel>?> =
        StyleProvider(preference)

    @Provides
    @Singleton
    fun provideFilterAroma(
        preference: SharedPreferenceProvider
    ): IFilterProvider<Any, List<AromaItemViewModel>?> =
        AromaProvider(preference)

}