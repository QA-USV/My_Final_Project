package ru.netology.fmhandroid.dto

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import ru.netology.fmhandroid.entity.ClaimCommentEntity
import ru.netology.fmhandroid.entity.UserEntity

@kotlinx.parcelize.Parcelize
data class FullClaim(
    @Embedded
    val claim: Claim,

    @Relation(
        entity = UserEntity::class,
        parentColumn = "creatorId",
        entityColumn = "id"
    )
    val creator: User,

    @Relation(
        entity = UserEntity::class,
        parentColumn = "executorId",
        entityColumn = "id"
    )
    val executor: User?,

    @Relation(
        entity = ClaimCommentEntity::class,
        parentColumn = "id",
        entityColumn = "claimId"
    )
    val comments: List<ClaimCommentWithCreator>?

) : Parcelable
