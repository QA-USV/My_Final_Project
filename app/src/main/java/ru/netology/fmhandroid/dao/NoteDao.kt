package ru.netology.fmhandroid.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.entity.NoteEntity
import java.time.LocalDateTime

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntity ORDER BY id DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notes: List<NoteEntity>)

    @Query("UPDATE NoteEntity Set deleted = 1 WHERE id = :id")
    suspend fun deleteNoteById(id: Int)
}

class Converters {
    @TypeConverter
    fun toAttachmentType(value: String) = LocalDateTime(value)

    @TypeConverter
    fun fromAttachment(value: AttachmentType) = value.name
}

