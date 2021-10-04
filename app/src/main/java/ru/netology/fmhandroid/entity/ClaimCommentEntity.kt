package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.ClaimComment

@Entity(tableName = "ClaimCommentEntity")
data class ClaimCommentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "claimId")
    val claimId: Int?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "creatorId")
    val creatorId: Int?,
    @ColumnInfo(name = "createDate")
    val createDate: Long?
) {
    fun toDto() = ClaimComment(
        id = id,
        claimId = claimId,
        description = description,
        creatorId = creatorId,
        createDate = createDate
    )
}

fun List<ClaimCommentEntity>.toDto(): List<ClaimComment> = map(ClaimCommentEntity::toDto)
fun List<ClaimComment>.toEntity(): List<ClaimCommentEntity> = map(ClaimComment::toEntity)
fun ClaimComment.toEntity() = ClaimCommentEntity(
    id = id,
    claimId = claimId,
    description = description,
    creatorId = creatorId,
    createDate = createDate
)

