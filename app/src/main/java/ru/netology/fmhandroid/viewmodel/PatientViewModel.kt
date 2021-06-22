package ru.netology.fmhandroid.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.db.AppDb
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.PatientStatusEnum
import ru.netology.fmhandroid.repository.patientRepository.PatientRepository
import ru.netology.fmhandroid.repository.patientRepository.PatientRepositoryImp
import ru.netology.fmhandroid.util.SingleLiveEvent

val EMPTY_PATIENT = Patient(
    id = 0,
    firstName = "",
    lastName = "",
    middleName = "",
    birthDate = "",
    currentAdmissionId = 0,
    deleted = false,
    status = PatientStatusEnum.ACTIVE
)

class PatientViewModel(application: Application) : AndroidViewModel(application) {

    private val patientRepository: PatientRepository =
        PatientRepositoryImp(
            AppDb.getInstance(context = application).patientDao(),
            AppDb.getInstance(context = application).admissionDao(),
            AppDb.getInstance(context = application).noteDao()
        )

    val data: LiveData<List<Patient>> = patientRepository.data
        .asLiveData(Dispatchers.Default)

    private val edited = MutableLiveData(EMPTY_PATIENT)
    private val _patientCreatedEvent = SingleLiveEvent<Unit>()
    val patientCreatedEvent: LiveData<Unit>
        get() = _patientCreatedEvent

    init {
        loadPatientsList()
    }

    fun loadPatientsList() {
        val it = edited.value ?: return
        viewModelScope.launch {
            try {
                patientRepository.getAllPatientsWithAdmissionStatus(it.status)
            } catch (e: Exception) {
                Toast.makeText(getApplication(), R.string.error_loading, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getAllPatientsWithAdmissionStatus(status: PatientStatusEnum) =
        viewModelScope.launch {
            try {
                patientRepository.getAllPatientsWithAdmissionStatus(status)
            } catch (e: Exception) {
                Toast.makeText(getApplication(), R.string.error_loading, Toast.LENGTH_LONG).show()

            }
        }

    fun save() {
        val it = edited.value ?: return
        viewModelScope.launch {
            try {
                patientRepository.savePatient(it)
                loadPatientsList()
            } catch (e: Exception) {
                Toast.makeText(getApplication(), R.string.error_saving, Toast.LENGTH_LONG).show()
            }
            _patientCreatedEvent.call()
        }
    }

    fun changePatientData(
        lastName: String,
        firstName: String,
        middleName: String,
        birthDate: String
    ) {
        val lastNameText = lastName.trim()
        val firstNameText = firstName.trim()
        val middleNameText = middleName.trim()
        val birthDateText = birthDate.trim()
        edited.value = edited.value?.copy(
            lastName = lastNameText,
            firstName = firstNameText,
            middleName = middleNameText,
            birthDate = birthDateText
        )
    }
}