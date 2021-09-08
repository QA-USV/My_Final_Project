package ru.netology.fmhandroid.dto

import java.time.LocalDateTime

data class Wish(
    val id: Int? = null,
    val patientId: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    val executorId: Int? = null,
    val createDate: LocalDateTime? = null,
    val planeExecuteDate: LocalDateTime? = null,
    val factExecuteDate: LocalDateTime? = null,
    val status: Status? = null,
    val deleted: Boolean = false,
) {
    enum class Status {
        CANCELLED,
        EXECUTED,
        IN_PROGRESS,
        OPEN
    }
}