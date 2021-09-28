
package ru.netology.fmhandroid.repository.patientRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.PatientApi
import ru.netology.fmhandroid.dao.AdmissionDao
import ru.netology.fmhandroid.dao.WishDao
import ru.netology.fmhandroid.dao.PatientDao
import ru.netology.fmhandroid.dto.Admission
import ru.netology.fmhandroid.dto.Wish
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.entity.toDto
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.utils.Utils.makeRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientRepositoryImp @Inject constructor(
    private val patientDao: PatientDao,
    private val admissionDao: AdmissionDao,
    private val wishDao: WishDao,
    private val patientApi: PatientApi
) : PatientRepository {

    override val data: Flow<List<Patient>>
        get() = patientDao.getAllPatients()
            .map { it.toDto() }
            .flowOn(Dispatchers.Default)

    override suspend fun getAllPatients(): List<Patient> = makeRequest(
        request = {patientApi.getAllPatients()},
        onSuccess = { body ->
            patientDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun getAllPatientsWithAdmissionStatus(
        status: Patient.Status
    ): Flow<List<Patient>> = flow {
        makeRequest(
            request = { patientApi.getAllPatientsWithAdmissionStatus(status) },
            onSuccess = { body ->
                patientDao.insert(body.toEntity())
                emit(body)
            }
        )
    }

    override suspend fun getPatientById(patientId: Int): Patient = makeRequest(
        request = { patientApi.getPatientById(patientId) },
        onSuccess = { body ->
            patientDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun savePatient(patient: Patient): Patient = makeRequest(
        request = { patientApi.savePatient(patient) },
        onSuccess = { body ->
            patientDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun editPatient(patient: Patient): Patient = makeRequest(
        request = { patientApi.updatePatient(patient) },
        onSuccess = { body ->
            patientDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun getPatientAdmissions(patientId: Int): List<Admission> =
        makeRequest(
            request = { patientApi.getPatientAdmissions(patientId) },
            onSuccess = { body ->
                admissionDao.insert(body.map { it.toEntity() })
                body
            }
        )

    override suspend fun getPatientNotes(patientId: Int): List<Wish> = makeRequest(
        request = { patientApi.getPatientNotes(patientId) },
        onSuccess = { body ->
            wishDao.insert(body.map { it.toEntity() })
            body
        }
    )
}
