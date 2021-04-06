package ru.netology.fmhandroid.entity

import androidx.room.Entity
import ru.netology.fmhandroid.dto.Patient
import java.util.*

@Entity
data class PatientEntity(
    val id : Long,
    val room_id: Long,
    var first_name: Char,
    var last_name: Char,
    var middle_name: Char,
    val birth_date: Date,
    val deleted: Boolean
) {
    fun toDto() = Patient(
        id,
        room_id,
        first_name,
        last_name,
        middle_name,
        birth_date,
        deleted
    )

    companion object {
        fun fromDto(dto: Patient) = PatientEntity(
            dto.id,
            dto.room_id,
            dto.first_name,
            dto.last_name,
            dto.middle_name,
            dto.birth_date,
            dto.deleted
        )

        fun fromApi(dto: Patient) = PatientEntity(
            dto.id,
            dto.room_id,
            dto.first_name,
            dto.last_name,
            dto.middle_name,
            dto.birth_date,
            dto.deleted
        )
    }

    fun List<PatientEntity>.toDto(): List<Patient> = map(PatientEntity::toDto)
    fun List<Patient>.toEntity(): List<PatientEntity> = map(PatientEntity::fromDto)
    fun List<Patient>.toApiEntity(): List<PatientEntity> = map(PatientEntity::fromApi)
}
