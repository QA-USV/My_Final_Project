package ru.netology.fmhandroid.repository.admissionRepository

import ru.netology.fmhandroid.api.AdmissionApi
import ru.netology.fmhandroid.dao.AdmissionDao
import ru.netology.fmhandroid.dto.Admission
import ru.netology.fmhandroid.entity.AdmissionEntity
import ru.netology.fmhandroid.error.ApiError
import ru.netology.fmhandroid.error.NetworkError
import ru.netology.fmhandroid.error.UnknownError
import java.io.IOException

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