package ru.netology.fmhandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.entity.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntity WHERE patient_id = :id ORDER BY id DESC")
    fun getAllNotesByPatientId(id: Long): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notes: List<NoteEntity>)
}
