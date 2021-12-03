package ru.netology.fmhandroid.api

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.netology.fmhandroid.auth.AppAuth

@InstallIn(ActivityComponent::class)
@Module(includes = [NetworkModule::class])
object AuthorizedNetworkModule {
    @Provides
    fun okhttp(interceptor: HttpLoggingInterceptor, appAuth: AppAuth): OkHttpClient {
        val authInterceptor = AuthInterceptor(appAuth)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }
}