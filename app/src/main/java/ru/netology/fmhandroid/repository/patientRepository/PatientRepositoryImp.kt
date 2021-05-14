package ru.netology.fmhandroid.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.Response
import ru.netology.fmhandroid.api.PatientApi
import ru.netology.fmhandroid.dao.AdmissionDao
import ru.netology.fmhandroid.dao.NoteDao
import ru.netology.fmhandroid.dao.PatientDao
import ru.netology.fmhandroid.dto.Admission
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.entity.PatientEntity
import ru.netology.fmhandroid.entity.toDto
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.exceptions.ApiException
import ru.netology.fmhandroid.exceptions.ServerException
import ru.netology.fmhandroid.exceptions.UnknownException
import java.io.IOException
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
                throw ApiException(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiException(response.code(), response.message())
            patientDao.insert(body.map { it.toEntity() })
        } catch (e: IOException) {
            throw ServerException
        } catch (e: Exception) {
            throw UnknownException
        }
    }

    override suspend fun getPatientById(patientId: Int): Patient {
        try {
            val response: Response<Patient> = PatientApi.service.getPatientById(patientId)
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiException(response.code(), response.message())
            patientDao.insert(body.toEntity())
            return body
        } catch (e: IOException) {
            throw ServerException
        } catch (e: Exception) {
            throw UnknownException
        }
    }

    override suspend fun savePatient(patient: Patient) {
        try {
            val response = PatientApi.service.savePatient(patient)
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiException(response.code(), response.message())
            patientDao.insert(body.toEntity())
        } catch (e: IOException) {
            throw ServerException
        } catch (e: Exception) {
            throw UnknownException
        }
    }

    override suspend fun editPatient(patient: Patient) {
        try {
            val response = PatientApi.service.editPatient(patient)
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }
            patientDao.insert(patient.toEntity())
        } catch (e: IOException) {
            throw ServerException
        } catch (e: Exception) {
            throw UnknownException
        }
    }

    override suspend fun getPatientAdmissions(patientId: Int): List<Admission> = makeRequest(
        request = { PatientApi.service.getPatientAdmissions(patientId) },
        onSuccess = { body -> admissionDao.insert(body.map { it.toEntity() })
            body
        }
    )

    override suspend fun getPatientNotes(patientId: Int) : List<Note> = makeRequest(
        request = { PatientApi.service.getPatientNotes(patientId) },
        onSuccess = { body -> noteDao.insert(body.map { it.toEntity() })
        body
        }
    )

    private suspend fun <T, R> makeRequest(
        request: suspend () -> Response<T>,
        onSuccess: suspend (body: T) -> R
    ) : R {
        try {
            val response = request()
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiException(response.code(), response.message())
            return onSuccess(body)
        } catch (e: IOException) {
            throw ServerException
        } catch (e: Exception) {
            throw UnknownException
        }
    }
}
