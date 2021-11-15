package ru.netology.fmhandroid.dto

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize
import ru.netology.fmhandroid.entity.ClaimCommentEntity
import ru.netology.fmhandroid.entity.UserEntity

@Parcelize
data class Claim(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    var executorId: Int? = null,
    val createDate: Long? = null,
    val planExecuteDate: Long? = null,
    val factExecuteDate: Long? = null,
    var status: Status? = null,
    val deleted: Boolean = false,
) : Parcelable {

    enum class Status {
        CANCELLED,
        EXECUTED,
        IN_PROGRESS,
        OPEN
    }
}
