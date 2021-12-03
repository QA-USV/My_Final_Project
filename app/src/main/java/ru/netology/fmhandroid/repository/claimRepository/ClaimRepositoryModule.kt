package ru.netology.fmhandroid.repository.claimRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
abstract class ClaimRepositoryModule {
    @Binds
    abstract fun bindClaimRepository(imp: ClaimRepositoryImpl): ClaimRepository
}
