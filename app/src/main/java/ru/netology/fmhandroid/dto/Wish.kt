package ru.netology.fmhandroid.dto

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize
import ru.netology.fmhandroid.entity.PatientEntity
import ru.netology.fmhandroid.entity.UserEntity
import java.time.LocalDateTime

@Parcelize
data class Wish(
    val id: Int? = null,
    val patientId: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    val executorId: Int? = null,
    val createDate: Long? = null,
    val planeExecuteDate: Long? = null,
    val factExecuteDate: Long? = null,
    val status: Status? = null,
    val deleted: Boolean = false,
): Parcelable {
    enum class Status {
        CANCELLED,
        EXECUTED,
        IN_PROGRESS,
        OPEN
    }
}
@kotlinx.parcelize.Parcelize
data class WishWithAllUsers(
    @Embedded
    val wish: Wish,

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
        entity = PatientEntity::class,
        parentColumn = "patientId",
        entityColumn = "id"
    )
    val patient: Patient

) : Parcelable
