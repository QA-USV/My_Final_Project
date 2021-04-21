package ru.netology.fmhandroid.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Patient

@Entity
data class PatientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val roomId: Long,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    val birthDate: String,
    val deleted: Boolean,
    val inHospice: Boolean
) {
    fun toDto() = Patient(
        id,
        roomId,
        firstName,
        lastName,
        middleName,
        birthDate,
        deleted,
        inHospice
    )

    companion object {
        fun fromDto(dto: Patient) = PatientEntity(
            dto.id,
            dto.roomId,
            dto.firstName,
            dto.lastName,
            dto.middleName,
            dto.birthDate,
            dto.deleted,
            dto.inHospice
        )
    }
}

fun List<PatientEntity>.toDto(): List<Patient> = map(PatientEntity::toDto)
fun List<Patient>.toEntity(): List<PatientEntity> = map(PatientEntity::fromDto)
