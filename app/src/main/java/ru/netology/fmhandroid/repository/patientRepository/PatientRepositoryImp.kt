package ru.netology.fmhandroid.repository

import androidx.room.Dao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.PatientApi
import ru.netology.fmhandroid.dao.AdmissionDao
import ru.netology.fmhandroid.dao.NoteDao
import ru.netology.fmhandroid.dao.PatientDao
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.entity.PatientEntity
import ru.netology.fmhandroid.entity.toDto
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

//    override suspend fun getAllPatients() {
//        try {
//            val response = PatientApi.service.getAllPatients()
//            if (!response.isSuccessful) {
//                throw ApiException(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiException(response.code(), response.message())
//            patientDao.insert(body.map { it.toEntity() })
//        } catch (e: IOException) {
//            throw ServerException
//        } catch (e: Exception) {
//            throw UnknownException
//        }
//    }
//
//    override suspend fun getPatientById(patientId: Int): Patient {
//        try {
//            val response: Response<Patient> = PatientApi.service.getPatientById(patientId)
//            if (!response.isSuccessful) {
//                throw ApiException(response.code(), response.message())
//            }
//
//            val body = response.body() ?: throw ApiException(response.code(), response.message())
//            patientDao.insert(body.toEntity())
//            return body
//        } catch (e: IOException) {
//            throw ServerException
//        } catch (e: Exception) {
//            throw UnknownException
//        }
//    }
//
//    override suspend fun savePatient(patient: Patient) {
//        try {
//            val response = PatientApi.service.savePatient(patient)
//            if (!response.isSuccessful) {
//                throw ApiException(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiException(response.code(), response.message())
//            patientDao.insert(body.toEntity())
//        } catch (e: IOException) {
//            throw ServerException
//        } catch (e: Exception) {
//            throw UnknownException
//        }
//    }
//
//    override suspend fun editPatient(patient: Patient) {
//        try {
//            val response = PatientApi.service.editPatient(patient)
//            if (!response.isSuccessful) {
//                throw ApiException(response.code(), response.message())
//            }
//            patientDao.insert(patient.toEntity())
//        } catch (e: IOException) {
//            throw ServerException
//        } catch (e: Exception) {
//            throw UnknownException
//        }
//    }
//
//    override suspend fun getPatientAdmissions(patientId: Int): List<Admission> {
//        try {
//            val response = PatientApi.service.getPatientAdmissions(patientId)
//            if (!response.isSuccessful) {
//                throw ApiException(response.code(), response.message())
//            }
//
//            val body = response.body() ?: throw ApiException(response.code(), response.message())
//            admissionDao.insert(body.map { it.toEntity() })
//            return body
//        } catch (e: IOException) {
//            throw ServerException
//        } catch (e: Exception) {
//            throw UnknownException
//        }
//    }

    override suspend fun getPatientNotes(patientId: Int): List<Note> {
//        try {
//            val response = PatientApi.service.getPatientNotes(patientId)
//            if (!response.isSuccessful) {
//                throw ApiException(response.code(), response.message())
//            }
//
//            val body = response.body() ?: throw ApiException(response.code(), response.message())
//            noteDao.insert(body.map { it.toEntity() })
//            return body
//        } catch (e: IOException) {
//            throw ServerException
//        } catch (e: Exception) {
//            throw UnknownException
//        }
        basisForServerRequests(response = PatientApi.service.getPatientNotes(patientId))
    }

    private suspend fun basisForServerRequests(
        patientId: Int,
        returnType: ReturnType,
        dao: Dao,
        apiService: PatientApi.Companion
    ): ReturnType {
        try {
            val response = when (returnType) {
                ReturnType.PATIENT ->
                    
                else ->
            }
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiException(response.code(), response.message())
        } catch (e: IOException) {
            throw ServerException
        } catch (e: Exception) {
            throw UnknownException
        }
    }
}
