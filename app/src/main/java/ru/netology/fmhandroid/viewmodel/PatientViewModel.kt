package ru.netology.fmhandroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.db.AppDb
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.PatientStatusEnum
import ru.netology.fmhandroid.repository.patientRepository.PatientRepository
import ru.netology.fmhandroid.repository.patientRepository.PatientRepositoryImp
import ru.netology.fmhandroid.util.SingleLiveEvent

private var PATIENT = Patient()

class PatientViewModel(application: Application) : AndroidViewModel(application) {

    private val patientRepository: PatientRepository =
        PatientRepositoryImp(
            AppDb.getInstance(context = application).patientDao(),
            AppDb.getInstance(context = application).admissionDao(),
            AppDb.getInstance(context = application).noteDao()
        )

    suspend fun data(): Flow<List<Patient>> =
        patientRepository.getAllPatientsWithAdmissionStatus(PatientStatusEnum.ACTIVE)

    private val _patientCreatedEvent = SingleLiveEvent<Unit>()
    val patientCreatedEvent: LiveData<Unit>
        get() = _patientCreatedEvent

    private val _loadPatientExceptionEvent = SingleLiveEvent<Unit>()
    val loadPatientExceptionEvent: LiveData<Unit>
        get() = _loadPatientExceptionEvent

    private val _savePatientExceptionEvent = SingleLiveEvent<Unit>()
    val savePatientExceptionEvent: LiveData<Unit>
        get() = _savePatientExceptionEvent

    init {
        loadPatientsList()
    }

    fun loadPatientsList() {
        viewModelScope.launch {
            try {
                patientRepository.getAllPatientsWithAdmissionStatus(PatientStatusEnum.ACTIVE)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllPatientsWithAdmissionStatus(status: PatientStatusEnum) =
        viewModelScope.launch {
            try {
                patientRepository.getAllPatientsWithAdmissionStatus(status)
            } catch (e: Exception) {
                _loadPatientExceptionEvent.call()
            }
        }

    fun save() {
        PATIENT.let {
            viewModelScope.launch {
                try {
                    patientRepository.savePatient(it)
                    loadPatientsList()
                } catch (e: Exception) {
                    e.printStackTrace()
                    _savePatientExceptionEvent.call()
                }
            }
            _patientCreatedEvent.call()
        }
        PATIENT = Patient()
    }

    fun changePatientData(
        lastName: String,
        firstName: String,
        middleName: String,
        birthDate: String
    ) {
        PATIENT = PATIENT.copy(
            lastName = lastName.trim(),
            firstName = firstName.trim(),
            middleName = middleName.trim(),
            birthDate = birthDate.trim()
        )
    }
}