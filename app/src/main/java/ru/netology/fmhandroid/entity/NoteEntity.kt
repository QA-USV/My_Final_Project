package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Note
import java.time.LocalDateTime

@Entity(tableName = "NoteEntity")
data class NoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "patientId")
    val patientId: Int? = null,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "creatorId")
    val creatorId: Int? = null,
    @ColumnInfo(name = "executorId")
    val executorId: Int? = null,
    @ColumnInfo(name = "createDate")
    val createDate: String? = null,
    @ColumnInfo(name = "planeExecuteDate")
    val planeExecuteDate: LocalDateTime? = null,
    @ColumnInfo(name = "factExecuteDate")
    val factExecuteDate: LocalDateTime? = null,
    @ColumnInfo(name = "status")
    val noteStatus: Note.Status? = null,
    @ColumnInfo(name = "comment")
    val comment: String? = null,
    @ColumnInfo(name = "deleted")
    val deleted: Boolean = false,
    /*
    Вопрос по данному полю остается отркрытым, стоит или не стоит его хранить в локальной БД?
     */
    @ColumnInfo(name = "shortExecutorName")
    val shortExecutorName: String,
    /*
    Вопрос по данному полю остается отркрытым, стоит или не стоит его хранить в локальной БД?
    */
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
    noteStatus = noteStatus,
    comment = comment,
    deleted = deleted,
    shortExecutorName = shortExecutorName,
    shortPatientName = shortPatientName
)
