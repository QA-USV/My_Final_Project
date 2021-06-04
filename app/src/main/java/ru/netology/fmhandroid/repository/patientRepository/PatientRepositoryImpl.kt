package ru.netology.fmhandroid.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
import ru.netology.fmhandroid.dto.PatientStatusEnum
import ru.netology.fmhandroid.entity.PatientEntity
import ru.netology.fmhandroid.entity.toDto
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.exceptions.ApiException
import ru.netology.fmhandroid.exceptions.ServerException
import ru.netology.fmhandroid.exceptions.UnknownException
import ru.netology.fmhandroid.repository.patientRepository.PatientRepository
import java.io.IOException


class PatientRepositoryImpl(
    private val patientDao: PatientDao,
    private val admissionDao: AdmissionDao,
    private val noteDao: NoteDao
) : PatientRepository {

    override fun data(): Flow<List<Patient>> =
        patientDao.getAllPatients()
            .map(List<PatientEntity>::toDto)
            .flowOn(Dispatchers.Default)


    override suspend fun getAllActivePatients(): List<Patient> = makeRequest(
        request = { PatientApi.service.getAllActivePatients() },
        onSuccess = { body ->
            patientDao.insert(body.map { it.toEntity() })
            body
        }
    )

    override suspend fun getAllPatientsWithAdmissionStatus(
        status: PatientStatusEnum
    ): List<Patient> = makeRequest(
        request = { PatientApi.service.getAllPatientsWithAdmissionStatus(status) },
        onSuccess = { body ->
            patientDao.insert(body.map { it.toEntity() })
            body
        }
    )

    override suspend fun getPatientById(patientId: Int): Patient = makeRequest(
        request = { PatientApi.service.getPatientById(patientId) },
        onSuccess = { body ->
            patientDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun savePatient(patient: Patient): Patient = makeRequest(
        request = { PatientApi.service.savePatient(patient) },
        onSuccess = { body ->
            patientDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun editPatient(patient: Patient): Patient = makeRequest(
        request = { PatientApi.service.updatePatient(patient) },
        onSuccess = { body ->
            patientDao.insert(body.toEntity())
            body
        }
    )


    override suspend fun getPatientAdmissions(patientId: Int): List<Admission> =
        makeRequest(
            request = { PatientApi.service.getPatientAdmissions(patientId) },
            onSuccess = { body ->
                admissionDao.insert(body.map { it.toEntity() })
                body
            }
        )

    override suspend fun getPatientNotes(patientId: Int): List<Note> = makeRequest(
        request = { PatientApi.service.getPatientNotes(patientId) },
        onSuccess = { body ->
            noteDao.insert(body.map { it.toEntity() })
            body
        }
    )

    private suspend fun <T, R> makeRequest(
        request: suspend () -> Response<T>,
        onSuccess: suspend (body: T) -> R
    ): R {
        try {
            val response = request()
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }
            val body =
                response.body() ?: throw ApiException(response.code(), response.message())
            return onSuccess(body)
        } catch (e: IOException) {
            throw ServerException
        } catch (e: Exception) {
            throw UnknownException
        }
    }
}
