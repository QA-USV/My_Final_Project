package ru.netology.fmhandroid.repository.patientRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.Admission
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Patient

interface PatientRepository {
    val data: Flow<List<Patient>>
    suspend fun getAllPatients()
    suspend fun getPatientById(patientId: Int) : Patient
    suspend fun savePatient(patient: Patient)
    suspend fun editPatient(patient: Patient)
    suspend fun getPatientAdmissions(patientId: Int) : List<Admission>
    suspend fun getPatientNotes(patientId: Int) : List<Note>
}