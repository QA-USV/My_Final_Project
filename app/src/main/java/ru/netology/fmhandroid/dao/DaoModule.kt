package ru.netology.fmhandroid.dao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.netology.fmhandroid.db.AppDb

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {
    @Provides
    fun providePatientDao(db: AppDb): PatientDao = db.patientDao()

    @Provides
    fun provideNoteDao(db: AppDb): WishDao = db.wishDao()

    @Provides
    fun provideAdmissionDao(db: AppDb): AdmissionDao = db.admissionDao()

    @Provides
    fun provideUserDao(db: AppDb): UserDao = db.userDao()
}