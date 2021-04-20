package ru.netology.fmhandroid.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Patient

@Entity
data class PatientEntity(
    @PrimaryKey
    val id : Int,
    val roomId: Int,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    val birthDate: String,
    val deleted: Boolean
) {
    fun toDto() = Patient(
        id,
        roomId,
        firstName,
        lastName,
        middleName,
        birthDate,
        deleted
    )

    companion object {
        fun fromDto(dto: Patient) = PatientEntity(
            dto.id,
            dto.roomId,
            dto.firstName,
            dto.lastName,
            dto.middleName,
            dto.birthDate,
            dto.deleted
        )
    }

    fun List<PatientEntity>.toDto(): List<Patient> = map(PatientEntity::toDto)
    fun List<Patient>.toEntity(): List<PatientEntity> = map(PatientEntity::fromDto)
}
