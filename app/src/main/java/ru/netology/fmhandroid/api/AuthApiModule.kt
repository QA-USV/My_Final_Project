package ru.netology.fmhandroid.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(ActivityComponent::class)
@Module(includes = [NonAuthorizedNetworkModule::class])
object AuthApiModule {
    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit
            .create(AuthApi::class.java)
    }
}