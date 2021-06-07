package ru.netology.fmhandroid.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.db.AppDb
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.PatientStatusEnum
import ru.netology.fmhandroid.model.FeedModel
import ru.netology.fmhandroid.model.FeedModelState
import ru.netology.fmhandroid.repository.patientRepository.PatientRepositoryImp
import ru.netology.fmhandroid.repository.patientRepository.PatientRepository
import ru.netology.fmhandroid.util.SingleLiveEvent

val emptyPatient = Patient(
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

    val data: LiveData<FeedModel> = patientRepository.data
        .map(::FeedModel)
        .asLiveData(Dispatchers.Default)

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState
    private val edited = MutableLiveData(emptyPatient)
    private val _patientCreated = SingleLiveEvent<Unit>()
    val patientCreated: LiveData<Unit>
        get() = _patientCreated

    init {
        loadPatientsList()
    }

    fun loadPatientsList() {
        val it = edited.value ?: return
        viewModelScope.launch {
            try {
                _dataState.value = FeedModelState(loading = true)
                patientRepository.getAllPatientsWithAdmissionStatus(it.status)
//            _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(errorLoading = true)
            }
        }
    }

    fun getAllPatientsWithAdmissionStatus(status: PatientStatusEnum) =
        viewModelScope.launch {
            try {
                _dataState.value = FeedModelState(loading = true)
                patientRepository.getAllPatientsWithAdmissionStatus(status)
//            _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(errorLoading = true)

            }
        }

    fun save() {
        val it = edited.value ?: return
        viewModelScope.launch {
            try {
                patientRepository.savePatient(it)
                _dataState.value = FeedModelState()
                loadPatientsList()
            } catch (e: Exception) {
//                        _dataState.value = FeedModelState(errorSaving = true)
            }
            _patientCreated.value = Unit
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