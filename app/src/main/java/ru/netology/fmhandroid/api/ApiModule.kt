package ru.netology.fmhandroid.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module(includes = [AuthorizedNetworkModule::class])
object ApiModule {
    @Provides
    @ActivityScoped
    fun provideClaimApi(retrofit: Retrofit): ClaimApi {
        return retrofit
            .create(ClaimApi::class.java)
    }

    @Provides
    @ActivityScoped
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit
            .create(UserApi::class.java)
    }

    @Provides
    @ActivityScoped
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit
            .create(NewsApi::class.java)
    }
}