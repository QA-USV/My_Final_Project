package ru.netology.fmhandroid.repository.noteRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.*

interface NoteRepository {
    val data: Flow<List<Note>>
    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun saveNote(note: Note): Note
    suspend fun editNote(note: Note): Note
    suspend fun getNoteById(id: Int): Note
    suspend fun saveNoteCommentById(noteId: Int, comment: String): Note
    suspend fun setNoteStatusById(noteId: Int, status: Note.Status): Note
}