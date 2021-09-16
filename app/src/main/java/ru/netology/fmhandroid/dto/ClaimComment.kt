package ru.netology.fmhandroid.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClaimComment(
    val id: Int? = null,
    val claimId: Int? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    val createDate: Long? = null,
): Parcelable