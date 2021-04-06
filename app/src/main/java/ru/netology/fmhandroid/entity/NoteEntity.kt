package ru.netology.fmhandroid.entity

import androidx.room.Entity
import ru.netology.fmhandroid.dto.Note
import java.util.*

@Entity
data class NoteEntity(
    val id: Long,
    val patient_id: Long,
    var description: Char,
    val creator_id: Long,
    val executor_id: Long,
    val create_date: Date,
    val plane_execute_date: Date,
    val fact_execute_date: Date,
    val status_id: Long,
    var comment: Char,
    val deleted: Boolean
) {
    fun toDto() = Note(
        id,
        patient_id,
        description,
        creator_id,
        executor_id,
        create_date,
        plane_execute_date,
        fact_execute_date,
        status_id,
        comment,
        deleted
    )

    companion object {
        fun fromDto(dto: Note) = NoteEntity(
            dto.id,
            dto.patient_id,
            dto.description,
            dto.creator_id,
            dto.executor_id,
            dto.create_date,
            dto.plane_execute_date,
            dto.fact_execute_date,
            dto.status_id,
            dto.comment,
            dto.deleted
        )

        fun fromApi(dto: Note) = NoteEntity(
            dto.id,
            dto.patient_id,
            dto.description,
            dto.creator_id,
            dto.executor_id,
            dto.create_date,
            dto.plane_execute_date,
            dto.fact_execute_date,
            dto.status_id,
            dto.comment,
            dto.deleted
        )
    }

    fun List<NoteEntity>.toDto(): List<Note> = map(NoteEntity::toDto)
    fun List<Note>.toEntity(): List<NoteEntity> = map(NoteEntity::fromDto)
    fun List<Note>.toApiEntity(): List<NoteEntity> = map(NoteEntity::fromApi)
}
