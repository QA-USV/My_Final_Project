package ru.netology.fmhandroid.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@InstallIn(ActivityComponent::class)
@Module(includes = [NetworkModule::class])
object NonAuthorizedNetworkModule {
    @Provides
    fun okhttp(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}