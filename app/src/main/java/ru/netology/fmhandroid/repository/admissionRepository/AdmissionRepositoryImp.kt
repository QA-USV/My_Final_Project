package ru.netology.fmhandroid.repository.admissionRepository

import ru.netology.fmhandroid.api.AdmissionApi
import ru.netology.fmhandroid.dao.AdmissionDao
import ru.netology.fmhandroid.dto.Admission
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdmissionRepositoryImp @Inject constructor(
    private val admissionDao: AdmissionDao,
    private val admissionApi: AdmissionApi
) : AdmissionRepository {

    override suspend fun saveAdmission(admission: Admission) {
        TODO("Not yet implemented")
    }

    override suspend fun updateAdmission(admission: Admission) {
        TODO("Not yet implemented")
    }

    override suspend fun getAdmissionById(id: Long): Admission {
        TODO("Not yet implemented")
    }
}