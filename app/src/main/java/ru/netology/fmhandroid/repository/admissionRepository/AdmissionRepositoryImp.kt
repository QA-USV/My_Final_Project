package ru.netology.fmhandroid.repository.admissionRepository

import ru.netology.fmhandroid.dao.AdmissionDao
import ru.netology.fmhandroid.dto.Admission

class AdmissionRepositoryImp(private val dao: AdmissionDao) : AdmissionRepository {

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