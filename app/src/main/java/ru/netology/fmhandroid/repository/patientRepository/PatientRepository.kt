package ru.netology.fmhandroid.repository.patientRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.Patient

interface PatientRepository {
    val data: Flow<List<Patient>>
    suspend fun getAllPatients()
    suspend fun savePatient(patient: Patient)
}