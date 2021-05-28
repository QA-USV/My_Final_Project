package ru.netology.fmhandroid.repository.admissionRepository

import ru.netology.fmhandroid.dto.Admission

interface AdmissionRepository {
    suspend fun saveAdmission(admission: Admission)
    suspend fun updateAdmission(admission: Admission)
    suspend fun getAdmissionById(id: Long): Admission
}