package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.NoteStatus

@Entity(tableName = "NoteEntity")
data class NoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "patientId")
    val patientId: Int,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "creatorId")
    val creatorId: Int,
    @ColumnInfo(name = "executorId")
    val executorId: Int,
    @ColumnInfo(name = "createDate")
    val createDate: String,
    @ColumnInfo(name = "planeExecuteDate")
    val planeExecuteDate: String,
    @ColumnInfo(name = "factExecuteDate")
    val factExecuteDate: String,
    @ColumnInfo(name = "statusId")
    val statusId: Int,
    @ColumnInfo(name = "status")
    val status: NoteStatus,
    @ColumnInfo(name = "comment")
    var comment: String,
    @ColumnInfo(name = "deleted")
    val deleted: Boolean
) {
    fun toDto() = Note(
        id = id,
        patientId = patientId,
        description = description,
        creatorId = creatorId,
        executorId = executorId,
        createDate = createDate,
        planeExecuteDate = planeExecuteDate,
        factExecuteDate = factExecuteDate,
        statusId = statusId,
        status = status,
        comment = comment,
        deleted = deleted
    )
}

fun List<NoteEntity>.toDto(): List<Note> = map(NoteEntity::toDto)
fun List<Note>.toEntity(): List<NoteEntity> = map(Note::toEntity)
fun Note.toEntity() = NoteEntity(
    id = id,
    patientId = patientId,
    description = description,
    creatorId = creatorId,
    executorId = executorId,
    createDate = createDate,
    planeExecuteDate = planeExecuteDate,
    factExecuteDate = factExecuteDate,
    statusId = statusId,
    status = status,
    comment = comment,
    deleted = deleted
)