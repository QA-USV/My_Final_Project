package ru.netology.fmhandroid.dto

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import ru.netology.fmhandroid.entity.ClaimCommentEntity

@kotlinx.parcelize.Parcelize
data class FullClaim(
    @Embedded
    val claim: Claim,

    @Relation(
        entity = ClaimCommentEntity::class,
        parentColumn = "id",
        entityColumn = "claimId"
    )
    val comments: List<ClaimComment>?

) : Parcelable
