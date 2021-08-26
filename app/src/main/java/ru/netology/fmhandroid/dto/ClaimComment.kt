package ru.netology.fmhandroid.dto

import java.time.LocalDateTime

data class ClaimComment(
    val id: Int,
    val claimId: Int,
    val description: String,
    val creatorId: Int,
    val createDate: LocalDateTime
    )
