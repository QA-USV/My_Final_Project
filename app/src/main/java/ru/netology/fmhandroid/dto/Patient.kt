package ru.netology.fmhandroid.dto

data class Patient(
    val id: Int,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    val birthDate: String,
    val currentAdmissionId: Int? = null,
    val deleted: Boolean = false,
    val status: PatientStatusEnum? = null,
    val shortPatientName: String
)

enum class PatientStatusEnum {
    ACTIVE,
    DISCHARGED,
    EXPECTED
}
