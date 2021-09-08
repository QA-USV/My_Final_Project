package ru.netology.fmhandroid.dto

import java.time.LocalDateTime

data class WishComment(
    val id: Int? = null,
    val wishId: Int? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    val createDate: LocalDateTime? = null,
)