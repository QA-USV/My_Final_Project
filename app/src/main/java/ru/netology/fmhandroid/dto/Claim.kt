package ru.netology.fmhandroid.dto

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize
import ru.netology.fmhandroid.entity.UserEntity

@Parcelize
data class Claim(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    val executorId: Int? = null,
    val createDate: String? = null,
    val planExecuteDate: String? = null,
    val factExecuteDate: String? = null,
    val status: Status? = null,
    val deleted: Boolean = false,
) : Parcelable {

    enum class Status {
        CANCELLED,
        EXECUTED,
        IN_PROGRESS,
        OPEN
    }
}

@kotlinx.parcelize.Parcelize
data class ClaimWithCreatorAndExecutor(
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
    val executor: User

) : Parcelable