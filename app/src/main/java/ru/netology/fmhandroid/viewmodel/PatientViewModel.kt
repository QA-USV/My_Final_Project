package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.Patient.Status
import ru.netology.fmhandroid.repository.patientRepository.PatientRepository
import ru.netology.fmhandroid.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val patientRepository: PatientRepository
) : ViewModel() {

    private var emptyPatient = Patient()

    val data: Flow<List<Patient>>
        get() = patientRepository.data

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
        viewModelScope.launch {
            patientRepository.getAllPatients(Status.EXPECTED)
                .collect()
        }
    }

    fun getAllPatientsWithAdmissionStatus(status: Status) {
        viewModelScope.launch {
            try {
                patientRepository.getAllPatients(status)
            } catch (e: Exception) {
                e.printStackTrace()
                _loadPatientExceptionEvent.call()
            }
        }
    }

    fun save() {
        emptyPatient.let {
            viewModelScope.launch {
                try {
                    patientRepository.savePatient(it)
                    emptyPatient = Patient()
                    _patientCreatedEvent.call()
                } catch (e: Exception) {
                    e.printStackTrace()
                    _savePatientExceptionEvent.call()
                }
            }
        }
    }

    fun changePatientData(
        lastName: String,
        firstName: String,
        middleName: String,
        birthDate: String
    ) {
        emptyPatient = emptyPatient.copy(
            lastName = lastName.trim(),
            firstName = firstName.trim(),
            middleName = middleName.trim(),
            birthday = birthDate.trim()
        )
    }
}