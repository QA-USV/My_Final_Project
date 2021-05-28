package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Patient

@Entity(tableName = "PatientEntity")
data class PatientEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long,
        @ColumnInfo(name = "roomId")
        val roomId: Long,
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
        val deleted: Boolean,
        @ColumnInfo(name = "inHospice")
        val inHospice: Boolean,
) {
    fun toDto() = Patient(
            id,
            roomId,
            firstName,
            lastName,
            middleName,
            shortPatientName,
            birthDate,
            deleted,
            inHospice
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
        deleted,
        inHospice
)
