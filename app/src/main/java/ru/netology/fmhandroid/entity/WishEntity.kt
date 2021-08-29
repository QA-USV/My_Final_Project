package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Wish
import java.time.LocalDateTime

@Entity(tableName = "WishEntity")
data class WishEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "patientId")
    val patientId: Int? = null,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "creatorId")
    val creatorId: Int? = null,
    @ColumnInfo(name = "executorId")
    val executorId: Int? = null,
    @ColumnInfo(name = "createDate")
    val createDate: LocalDateTime? = null,
    @ColumnInfo(name = "planeExecuteDate")
    val planeExecuteDate: LocalDateTime? = null,
    @ColumnInfo(name = "factExecuteDate")
    val factExecuteDate: LocalDateTime? = null,
    @ColumnInfo(name = "status")
    val wishStatus: Wish.Status? = null,
    @ColumnInfo(name = "deleted")
    val deleted: Boolean = false,
    @ColumnInfo(name = "shortExecutorName")
    val shortExecutorName: String,
    @ColumnInfo(name = "shortPatientName")
    val shortPatientName: String
) {
    fun toDto() = Wish(
        id = id,
        patientId = patientId,
        title = title,
        description = description,
        creatorId = creatorId,
        executorId = executorId,
        createDate = createDate,
        planeExecuteDate = planeExecuteDate,
        factExecuteDate = factExecuteDate,
        wishStatus = wishStatus,
        deleted = deleted,
        shortExecutorName = shortExecutorName,
        shortPatientName = shortPatientName
    )
}

fun List<WishEntity>.toDto(): List<Wish> = map(WishEntity::toDto)
fun List<Wish>.toEntity(): List<WishEntity> = map(Wish::toEntity)
fun Wish.toEntity() = WishEntity(
    id = id,
    patientId = patientId,
    title = title,
    description = description,
    creatorId = creatorId,
    executorId = executorId,
    createDate = createDate,
    planeExecuteDate = planeExecuteDate,
    factExecuteDate = factExecuteDate,
    wishStatus = wishStatus,
    deleted = deleted,
    shortExecutorName = shortExecutorName,
    shortPatientName = shortPatientName
)
