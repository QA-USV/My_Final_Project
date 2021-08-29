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
    fun providePatientApi(): PatientApi {
        return retrofit(okhttp(loggingInterceptor()))
            .create(PatientApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWishApi(): WishApi {
        return retrofit(okhttp(loggingInterceptor()))
            .create(WishApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAdmissionApi(): AdmissionApi {
        return retrofit(okhttp(loggingInterceptor()))
            .create(AdmissionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        return retrofit(okhttp(loggingInterceptor()))
            .create(UserApi::class.java)
    }
}