package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Patient

@Entity(tableName = "PatientEntity")
data class PatientEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "roomId")
    val roomId: Int,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @ColumnInfo(name = "middleName")
    val middleName: String,
    @ColumnInfo(name = "shortPatientName")
    val shortPatientName: String,
    @ColumnInfo(name = "birthDate")
    val birthDate: String,
    @ColumnInfo(name = "deleted")
    val deleted: Boolean
) {
    fun toDto() = Patient(
        id,
        roomId,
        firstName,
        lastName,
        middleName,
        shortPatientName,
        birthDate,
        deleted
    )
}

fun List<PatientEntity>.toDto(): List<Patient> = map(PatientEntity::toDto)
fun List<Patient>.toListEntity(): List<PatientEntity> = map(Patient::toEntity)
fun Patient.toEntity(): PatientEntity = PatientEntity(
    id,
    roomId,
    firstName,
    lastName,
    middleName,
    shortPatientName,
    birthDate,
    deleted
)


