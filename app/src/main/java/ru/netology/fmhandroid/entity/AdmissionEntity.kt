package ru.netology.fmhandroid.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Admission

@Entity
data class AdmissionEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val patientId: Long,
        val comment: String,
        val dateIn: String,
        val dateOut: String,
        val factIn: String,
        val factOut: String,
        val deleted: Boolean
) {
    fun toDto() = Admission(
            id,
            patientId,
            comment,
            dateIn,
            dateOut,
            factIn,
            factOut,
            deleted
    )

    companion object {
        fun fromDto(dto: Admission) = AdmissionEntity(
                dto.id,
                dto.patientId,
                dto.comment,
                dto.dateIn,
                dto.dateOut,
                dto.factIn,
                dto.factOut,
                dto.deleted
        )
    }
}

fun List<AdmissionEntity>.toDto(): List<Admission> = map(AdmissionEntity::toDto)
fun List<Admission>.toEntity(): List<AdmissionEntity> = map(AdmissionEntity::fromDto)