package ru.netology.fmhandroid.dto

import java.time.LocalDateTime

data class Claim(
    val id: Int,
    val title: String,
    val description: String,
    val creatorId: Int,
    val executorId: Int,
    val createDate: LocalDateTime,
    val planExecuteDate: LocalDateTime,
    val factExecuteDate: LocalDateTime,
    val status: ClaimStatus,
    val deleted: Boolean
)

enum class ClaimStatus {
    CANCELLED,
    EXECUTED,
    IN_PROGRESS,
    OPEN
}
