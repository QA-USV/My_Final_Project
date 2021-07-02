package ru.netology.fmhandroid.repository.admissionRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AdmissionRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAdmissionRepository(imp: AdmissionRepositoryImp): AdmissionRepository
}