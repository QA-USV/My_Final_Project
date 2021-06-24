package ru.netology.fmhandroid.dto

data class Patient(
    val id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var middleName: String = "",
    val birthDate: String = "",
    val currentAdmissionId: Int = 0,
    val deleted: Boolean = false,
    val status: Status = Status.ACTIVE
) {

    enum class Status {
        ACTIVE,
        EXPECTED,
        DISCHARGED
    }
}
