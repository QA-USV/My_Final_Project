package ru.netology.fmhandroid.repository.patientRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.netology.fmhandroid.api.PatientApi
import ru.netology.fmhandroid.dao.AdmissionDao
import ru.netology.fmhandroid.dao.NoteDao
import ru.netology.fmhandroid.dao.PatientDao
import ru.netology.fmhandroid.dto.*
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.util.makeRequest

class PatientRepositoryImp(
    private val patientDao: PatientDao,
    private val admissionDao: AdmissionDao,
    private val noteDao: NoteDao
) : PatientRepository {

    override suspend fun getAllPatientsWithAdmissionStatus(
        status: PatientStatusEnum
    ): Flow<List<Patient>> = flow {
        makeRequest(
            request = { PatientApi.service.getAllPatientsWithAdmissionStatus(status) },
            onSuccess = { body ->
                patientDao.insert(body.toEntity())
                emit(body)
            }
        )
    }

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
}
