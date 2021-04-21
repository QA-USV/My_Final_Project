package ru.netology.fmhandroid.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Note

@Entity
data class NoteEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val patientId: Long,
        var description: String,
        val creatorId: Long,
        val executorId: Long,
        val createDate: String,
        val planeExecuteDate: String,
        val factExecuteDate: String,
        val statusId: Long,
        var comment: String,
        val deleted: Boolean,
) {
    fun toDto() = Note(
            id,
            patientId,
            description,
            creatorId,
            executorId,
            createDate,
            planeExecuteDate,
            factExecuteDate,
            statusId,
            comment,
            deleted
    )

    companion object {
        fun fromDto(dto: Note) = NoteEntity(
                dto.id,
                dto.patientId,
                dto.description,
                dto.creatorId,
                dto.executorId,
                dto.createDate,
                dto.planeExecuteDate,
                dto.factExecuteDate,
                dto.statusId,
                dto.comment,
                dto.deleted
        )
    }
}

fun List<NoteEntity>.toDto(): List<Note> = map(NoteEntity::toDto)
fun List<Note>.toEntity(): List<NoteEntity> = map(NoteEntity::fromDto)
