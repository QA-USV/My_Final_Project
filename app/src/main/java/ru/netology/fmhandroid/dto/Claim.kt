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
    val title: String,
    val description: String,
    val creatorId: Int,
    var executorId: Int? = null,
    val createDate: Long,
    val planExecuteDate: Long,
    val factExecuteDate: Long? = null,
    var status: Status,
) : Parcelable {

    enum class Status {
        CANCELLED,
        EXECUTED,
        IN_PROGRESS,
        OPEN
    }
}
