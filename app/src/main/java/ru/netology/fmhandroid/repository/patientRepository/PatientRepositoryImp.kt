package ru.netology.fmhandroid.repository.patientRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.netology.fmhandroid.api.PatientApi
import ru.netology.fmhandroid.dao.PatientDao
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.entity.*
import ru.netology.fmhandroid.error.*
import java.io.IOException

class PatientRepositoryImp(
        private val dao: PatientDao
) : PatientRepository {

    override val data = dao.getAllPatients()
            .map(List<PatientEntity>::toDto)
            .flowOn(Dispatchers.Default)

    override suspend fun getAllPatients() {
        try {
            val response = PatientApi.service.getAllPatients()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(body.toEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun savePatient(patient: Patient) {
        dao.insert(PatientEntity.fromDto(patient))
        try {
            val response = PatientApi.service.savePatient(patient)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}