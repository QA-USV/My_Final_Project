package ru.netology.fmhandroid.dto

import java.time.LocalDateTime

data class Wish(
    val id: Int? = null,
    val patientId: Int? = null,
    val title: String? = null,
    var description: String? = null,
    val creatorId: Int? = null,
    val executorId: Int? = null,
    val createDate: LocalDateTime? = null,
    val planeExecuteDate: LocalDateTime? = null,
    val factExecuteDate: LocalDateTime? = null,
    val wishStatus: Status? = null,
    val deleted: Boolean = false,
    val shortExecutorName: String? = null,
    val shortPatientName: String? = null
) {
    enum class Status {
        CANCELLED,
        EXECUTED,
        IN_PROGRESS,
        OPEN
    }
}