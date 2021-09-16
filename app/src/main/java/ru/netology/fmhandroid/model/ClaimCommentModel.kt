package ru.netology.fmhandroid.model

import ru.netology.fmhandroid.dto.ClaimComment

data class ClaimCommentModel(
    val comments: List<ClaimComment> = emptyList(),
    val loading: Boolean = false,
    val error: Boolean = false
)
