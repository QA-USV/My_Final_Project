package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Claim
import java.time.LocalDateTime

@Entity(tableName = "ClaimEntity")
data class ClaimEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "creatorId")
    val creatorId: Int?,
    @ColumnInfo(name = "executorId")
    val executorId: Int = 0,
    @ColumnInfo(name = "createDate")
    val createDate: Long?,
    @ColumnInfo(name = "planExecuteDate")
    val planExecuteDate: Long?,
    @ColumnInfo(name = "factExecuteDate")
    val factExecuteDate: Long?,
    @ColumnInfo(name = "status")
    val status: Claim.Status?,
    @ColumnInfo(name = "deleted")
    val deleted: Boolean
) {
    fun toDto() = Claim(
        id = id,
        title = title,
        description = description,
        creatorId = creatorId,
        executorId = executorId,
        createDate = createDate,
        planExecuteDate = planExecuteDate,
        factExecuteDate = factExecuteDate,
        status = status,
        deleted = deleted
    )
}

fun List<ClaimEntity>.toDto(): List<Claim> = map(ClaimEntity::toDto)
fun List<Claim>.toEntity(): List<ClaimEntity> = map(Claim::toEntity)
fun Claim.toEntity() = ClaimEntity(
    id = id,
    title = title,
    description = description,
    creatorId = creatorId,
    executorId = executorId,
    createDate = createDate,
    planExecuteDate = planExecuteDate,
    factExecuteDate = factExecuteDate,
    status = status,
    deleted = deleted
)
