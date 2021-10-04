package ru.netology.fmhandroid.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Provides
    @Singleton
    fun provideClaimApi(): ClaimApi {
        return retrofit(okhttp(loggingInterceptor()))
            .create(ClaimApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        return retrofit(okhttp(loggingInterceptor()))
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return retrofit(okhttp(loggingInterceptor()))
            .create(NewsApi::class.java)
    }
}