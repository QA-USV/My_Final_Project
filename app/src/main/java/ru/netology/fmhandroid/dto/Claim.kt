package ru.netology.fmhandroid.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Claim(
    val id: Int,
    val title: String,
    val description: String,
    val creatorId: Int,
    val executorId: Int,
    val createDate: LocalDateTime,
    val planExecuteDate: LocalDateTime,
    val factExecuteDate: LocalDateTime,
    val statusId: Int,
    val deleted: Boolean
) : Parcelable
