package ru.netology.fmhandroid.dto

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize
import ru.netology.fmhandroid.entity.UserEntity

@Parcelize
data class ClaimComment(
    val id: Int? = null,
    val claimId: Int? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    val createDate: Long? = null,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class ClaimCommentWithCreator(
    @Embedded
    val claimComment: ClaimComment,

    @Relation(
        entity = UserEntity::class,
        parentColumn = "creatorId",
        entityColumn = "id"
    )
    val creator: User
) : Parcelable