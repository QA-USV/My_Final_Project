package ru.netology.fmhandroid.dto

data class Patient(
    val id: Int = 0,
    var firstName: String? = null,
    var lastName: String? = null,
    var middleName: String? = null,
    val birthday: String? = null,
    val currentAdmissionId: Int = 0,
    val deleted: Boolean = false,
    val admissionsStatus: Status = Status.EXPECTED
) {

    enum class Status {
        ACTIVE,
        EXPECTED,
        DISCHARGED
    }
}
