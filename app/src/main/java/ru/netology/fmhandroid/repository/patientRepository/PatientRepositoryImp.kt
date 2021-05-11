package ru.netology.fmhandroid.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.PatientApiService
import ru.netology.fmhandroid.dao.PatientDao
import ru.netology.fmhandroid.dto.Admission
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.entity.PatientEntity
import ru.netology.fmhandroid.entity.toDto
import ru.netology.fmhandroid.error.ApiError
import ru.netology.fmhandroid.error.ServerError
import ru.netology.fmhandroid.error.UnknownError
import java.io.IOException
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.repository.patientRepository.PatientRepository

class PatientRepositoryImp(
    private val dao: PatientDao
) : PatientRepository {

    override val data = dao.getAllPatients()
        .map(List<PatientEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun getPatientById(patientId: Int): Patient {
        try {
            val response = PatientApiService.PatientApi.service.getPatientById(patientId)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(body.toEntity())
            return body
        } catch (e: IOException) {
            throw ServerError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun savePatient(patient: Patient) {
        try {
            if (patient.id == 0) {
                val response = PatientApiService.PatientApi.service.savePatient(patient)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                val body = response.body() ?: throw ApiError(response.code(), response.message())
                dao.insert(body.toEntity())
            } else {
                val response = PatientApiService.PatientApi.service.editPatient(patient)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                dao.insert(patient.toEntity())
            }
        } catch (e: IOException) {
            throw ServerError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getPatientAdmissions(patientId: Int): List<Admission> {
        TODO("Not yet implemented")
    }

    override suspend fun getPatientNotes(patientId: Int): List<Note> {
        TODO("Not yet implemented")
    }
}
