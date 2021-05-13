package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Admission

@Entity(tableName = "AdmissionEntity")
data class AdmissionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "patientId")
    val patientId: Int,
    @ColumnInfo(name = "planDateIn")
    val planDateIn: String,
    @ColumnInfo(name = "planDateOut")
    val planDateOut: String,
    @ColumnInfo(name = "factDateIn")
    val factDateIn: String,
    @ColumnInfo(name = "factDateOut")
    val factDateOut: String,
    @ColumnInfo(name = "admStatusId")
    val admStatusId: Int,
    @ColumnInfo(name = "roomId")
    val roomId: Int,
    @ColumnInfo(name = "comment")
    val comment: String,
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
        admStatusId = admStatusId,
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
    admStatusId = admStatusId,
    roomId = roomId,
    comment = comment,
    deleted = deleted
)
