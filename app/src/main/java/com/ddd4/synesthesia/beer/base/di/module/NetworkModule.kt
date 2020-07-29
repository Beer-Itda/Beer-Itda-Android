package com.ddd4.synesthesia.beer.base.di.module

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class NetworkModule {
    @Provides
    @Named("interceptor")
    fun httpInterceptor() {

    }

}