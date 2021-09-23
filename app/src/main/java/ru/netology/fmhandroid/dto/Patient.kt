package ru.netology.fmhandroid.dto

import java.time.LocalDateTime

data class Patient(
    val id: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val birthDate: Long? = null,
    val currentAdmissionId: Int? = null,
    val deleted: Boolean = false,
){
    enum class Status {
        ACTIVE,
        EXPECTED,
        DISCHARGED
    }
}