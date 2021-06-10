package ru.netology.fmhandroid.repository.noteRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.*

interface NoteRepository {
    val data: Flow<List<Note>>
    suspend fun getAllNotes() : List<Note>
    suspend fun saveNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun getNoteById(id: Long): Note
}