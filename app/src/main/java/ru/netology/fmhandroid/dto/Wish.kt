package ru.netology.fmhandroid.dto

import java.time.LocalDateTime

data class Wish(
    val id: Int = 0,
    val patientId: Int? = null,
    val title: String = "",
    var description: String = "",
    val creatorId: Int? = null,
    val executorId: Int? = null,
    val createDate: LocalDateTime? = null,
    val planeExecuteDate: LocalDateTime? = null,
    val factExecuteDate: LocalDateTime? = null,
    val wishStatus: Status? = null,
    val deleted: Boolean = false,
    val shortExecutorName: String = "",
    val shortPatientName: String = ""
) {
    enum class Status {
        CANCELLED,
        EXECUTED,
        IN_PROGRESS,
        OPEN
    }
}