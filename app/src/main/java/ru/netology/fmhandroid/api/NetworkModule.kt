package ru.netology.fmhandroid.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.fmhandroid.BuildConfig
import ru.netology.fmhandroid.api.qualifier.Authorized
import ru.netology.fmhandroid.api.qualifier.NonAuthorized
import ru.netology.fmhandroid.auth.AppAuth
import ru.netology.fmhandroid.repository.authRepository.AuthRepository
import javax.inject.Provider

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun loggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

    @NonAuthorized
    @Provides
    fun provideNonAuthorizedRetrofit(@NonAuthorized client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()

    @Authorized
    @Provides
    fun provideAuthorizedRetrofit(@Authorized client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .build()

    @Authorized
    @Provides
    fun authorizedOkhttp(
        interceptor: HttpLoggingInterceptor,
        appAuth: AppAuth,
        authRepositoryProvider: Provider<AuthRepository>
    ): OkHttpClient {
        val authInterceptor = AuthInterceptor(appAuth)
        val unauthorizedInterceptor = UnauthorizedInterceptor(authRepositoryProvider, appAuth)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(unauthorizedInterceptor)
            .build()
    }

    @NonAuthorized
    @Provides
    fun nonAuthorizedOkhttp(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
}