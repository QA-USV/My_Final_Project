package ru.netology.fmhandroid.repository.claimRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ClaimRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindClaimRepository(imp: ClaimRepositoryImpl): ClaimRepository
}
