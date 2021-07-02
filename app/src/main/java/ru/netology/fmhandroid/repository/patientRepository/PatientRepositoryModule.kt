package ru.netology.fmhandroid.repository.patientRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class PatientRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPatientRepository(imp: PatientRepositoryImp): PatientRepository
}