package ru.netology.fmhandroid.model

import ru.netology.fmhandroid.dto.Patient

data class PatientModel(
    val patients: List<Patient> = emptyList(),
    val empty: Boolean = false,
)