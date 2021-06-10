package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.NoteStatus
import ru.netology.fmhandroid.enum.ExecutionPriority

@Entity(tableName = "NoteEntity")
data class NoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "patientId")
    val patientId: Int? = null,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "creatorId")
    val creatorId: Int? = null,
    @ColumnInfo(name = "executorId")
    val executorId: Int? = null,
    @ColumnInfo(name = "createDate")
    val createDate: String? = null,
    @ColumnInfo(name = "planeExecuteDate")
    val planeExecuteDate: String,
    @ColumnInfo(name = "factExecuteDate")
    val factExecuteDate: String,
    @ColumnInfo(name = "executionPriority")
    val executionPriority: ExecutionPriority,
    @ColumnInfo(name = "statusId")
    val noteStatus: NoteStatus? = null,
    @ColumnInfo(name = "comment")
    var comment: String? = null,
    @ColumnInfo(name = "deleted")
    val deleted: Boolean = false,
    @ColumnInfo(name = "shortExecutorName")
    val shortExecutorName: String,
    @ColumnInfo(name = "shortPatientName")
    val shortPatientName: String
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
        executionPriority = executionPriority,
        noteStatus = noteStatus,
        comment = comment,
        deleted = deleted,
        shortExecutorName = shortExecutorName,
        shortPatientName = shortPatientName
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
    executionPriority = executionPriority,
    noteStatus = noteStatus,
    comment = comment,
    deleted = deleted,
    shortExecutorName = shortExecutorName,
    shortPatientName = shortPatientName
)
