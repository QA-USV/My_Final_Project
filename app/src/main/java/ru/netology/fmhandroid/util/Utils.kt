package ru.netology.fmhandroid.util

import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.PatientStatusEnum

class Utils {
    companion object {
        val emptyPatient = Patient(
            id = 0,
        firstName = "",
        lastName = "",
        middleName = "",
        birthDate = "",
        currentAdmissionId = 0,
        deleted = false,
        status = PatientStatusEnum.EXPECTED
        )
    }
}