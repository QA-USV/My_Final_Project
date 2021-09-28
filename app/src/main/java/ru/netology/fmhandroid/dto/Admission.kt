package ru.netology.fmhandroid.dto

import java.time.LocalDateTime

data class Admission(
    val id: Int? = null,
    val patientId: Int? = null,
    val planDateIn: Long? = null,
    val planDateOut: Long? = null,
    val factDateIn: Long? = null,
    val factDateOut: Long? = null,
    val status: Status? = null,
    val roomId: Int? = null,
    val comment: String? = null,
    val deleted: Boolean = false,
) {
    enum class Status {
        IN_HOSPICE,
        AWAIT,
        OUT_HOSPICE
    }
}