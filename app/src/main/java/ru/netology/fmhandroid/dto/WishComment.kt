package ru.netology.fmhandroid.dto

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize
import ru.netology.fmhandroid.entity.UserEntity

@Parcelize
data class WishComment(
    val id: Int? = null,
    val wishId: Int? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    val createDate: Long? = null,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class WishCommentWithCreator(
    @Embedded
    val wishComment: WishComment,

    @Relation(
        entity = UserEntity::class,
        parentColumn = "creatorId",
        entityColumn = "id"
    )
    val creator: User
) : Parcelable
