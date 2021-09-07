package ru.netology.fmhandroid.dto

import java.time.LocalDateTime

data class ClaimComment(
    val id: Int? = null,
    val claimId: Int? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    val createDate: LocalDateTime? = null
    )
