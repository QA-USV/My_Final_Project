package ru.netology.fmhandroid.dto

data class Patient(
    val id: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val birthDate: String? = null,
    val currentAdmissionId: Int? = null,
    val deleted: Boolean = false,
){
    enum class Status {
        ACTIVE,
        EXPECTED,
        DISCHARGED
    }
}