package ru.netology.fmhandroid.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.PatientApi
import ru.netology.fmhandroid.dao.AdmissionDao
import ru.netology.fmhandroid.dao.NoteDao
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
    private val patientDao: PatientDao,
    private val admissionDao: AdmissionDao,
    private val noteDao: NoteDao
) : PatientRepository {

    override val data = patientDao.getAllPatients()
        .map(List<PatientEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun getAllPatients() {
        try {
            val response = PatientApi.service.getAllPatients()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            patientDao.insert(body.map { it.toEntity() })
        } catch (e: IOException) {
            throw ServerError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getPatientById(patientId: Int): Patient {
        try {
            val response = PatientApi.service.getPatientById(patientId)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            patientDao.insert(body.toEntity())
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
                val response = PatientApi.service.savePatient(patient)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                val body = response.body() ?: throw ApiError(response.code(), response.message())
                patientDao.insert(body.toEntity())
            } else {
                val response = PatientApi.service.editPatient(patient)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                patientDao.insert(patient.toEntity())
            }
        } catch (e: IOException) {
            throw ServerError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getPatientAdmissions(patientId: Int): List<Admission> {
        try {
            val response = PatientApi.service.getPatientAdmissions(patientId)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            admissionDao.insert(body.map { it.toEntity() })
            return body
        } catch (e: IOException) {
            throw ServerError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getPatientNotes(patientId: Int): List<Note> {
        try {
            val response = PatientApi.service.getPatientNotes(patientId)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            noteDao.insert(body.map { it.toEntity() })
            return body
        } catch (e: IOException) {
            throw ServerError
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}
