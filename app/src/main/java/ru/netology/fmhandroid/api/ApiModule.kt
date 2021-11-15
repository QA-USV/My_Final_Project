package ru.netology.fmhandroid.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.netology.fmhandroid.api.NetworkModule.providesRetrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
object ApiModule {
    @Provides
    @Singleton
    fun provideClaimApi(): ClaimApi {
        return providesRetrofit()
            .create(ClaimApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        return providesRetrofit()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return providesRetrofit()
            .create(NewsApi::class.java)
    }
}