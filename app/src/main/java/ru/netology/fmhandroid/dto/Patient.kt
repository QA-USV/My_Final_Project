package ru.netology.fmhandroid.dto

data class Patient(
    val id: Int,
    var firstName: String?,
    var lastName: String?,
    var middleName: String?,
    val birthday: String?,
    val currentAdmissionId: Int? = null,
    val deleted: Boolean = false,
    val admissionsStatus: Status? = null,
    val shortPatientName: String
){
    enum class Status {
        ACTIVE,
        EXPECTED,
        DISCHARGED
    }
}
