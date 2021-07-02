package ru.netology.fmhandroid.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.netology.fmhandroid.repository.admissionRepository.AdmissionRepository
import javax.inject.Inject

@HiltViewModel
class AdmissionViewModel @Inject constructor(
    private val admissionRepository: AdmissionRepository
) : ViewModel() {

    suspend fun saveAdmission() {
        TODO("Not yet implemented")
    }

    suspend fun updateAdmission() {
        TODO("Not yet implemented")
    }

    suspend fun getAdmissionById() {
        TODO("Not yet implemented")
    }
}