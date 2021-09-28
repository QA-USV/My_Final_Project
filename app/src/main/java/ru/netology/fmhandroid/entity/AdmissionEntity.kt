package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Admission
import java.time.LocalDateTime

@Entity(tableName = "AdmissionEntity")
data class AdmissionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "patientId")
    val patientId: Int?,
    @ColumnInfo(name = "planDateIn")
    val planDateIn: Long?,
    @ColumnInfo(name = "planDateOut")
    val planDateOut: Long?,
    @ColumnInfo(name = "factDateIn")
    val factDateIn: Long?,
    @ColumnInfo(name = "factDateOut")
    val factDateOut: Long?,
    @ColumnInfo(name = "admStatusId")
    val status: Admission.Status?,
    @ColumnInfo(name = "roomId")
    val roomId: Int?,
    @ColumnInfo(name = "comment")
    val comment: String?,
    @ColumnInfo(name = "deleted")
    val deleted: Boolean = false,
) {
    fun toDto() = Admission(
        id = id,
        patientId = patientId,
        planDateIn = planDateIn,
        planDateOut = planDateOut,
        factDateIn = factDateIn,
        factDateOut = factDateOut,
        status = status,
        roomId = roomId,
        comment = comment,
        deleted = deleted
    )
}

fun List<AdmissionEntity>.toDto(): List<Admission> = map(AdmissionEntity::toDto)
fun List<Admission>.toEntity(): List<AdmissionEntity> = map(Admission::toEntity)
fun Admission.toEntity() = AdmissionEntity(
    id = id,
    patientId = patientId,
    planDateIn = planDateIn,
    planDateOut = planDateOut,
    factDateIn = factDateIn,
    factDateOut = factDateOut,
    status = status,
    roomId = roomId,
    comment = comment,
    deleted = deleted
)
