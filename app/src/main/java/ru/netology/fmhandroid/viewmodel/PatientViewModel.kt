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
import ru.netology.fmhandroid.repository.PatientRepositoryImp
import ru.netology.fmhandroid.repository.patientRepository.PatientRepository
import ru.netology.fmhandroid.repository.patientRepository.PatientRepositoryImp
import ru.netology.fmhandroid.util.SingleLiveEvent

val emptyPatient = Patient(
        id = 0,
        firstName = "",
        lastName = "",
        middleName = "",
        birthDate = "",
        deleted = false,
        status = PatientStatusEnum.EXPECTED
)

class PatientViewModel(application: Application) : AndroidViewModel(application) {

    private val patientRepository: PatientRepository =
            PatientRepositoryImp(AppDb.getInstance(context = application).patientDao())

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

    fun loadPatientsList() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(loading = true)
            patientRepository.getAllPatientsWithAdmissionStatus()
//            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(errorLoading = true)
        }
    }

    fun save() {
        edited.value?.let {
            viewModelScope.launch {
                if (it.lastName.isNotBlank() && it.firstName.isNotBlank() && it.middleName.isNotBlank()) {
                    try {
                        patientRepository.savePatient(it)
                        _dataState.value = FeedModelState()
                        loadPatientsList()
                    } catch (e: Exception) {
//                        _dataState.value = FeedModelState(errorSaving = true)
                    }
                    _patientCreated.value = Unit
                } else {
                    Toast.makeText(getApplication(), R.string.toast_empty_field, Toast.LENGTH_LONG)
                            .show()
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

    suspend fun getAllPatients() {
        TODO("Not yet implemented")
    }
}