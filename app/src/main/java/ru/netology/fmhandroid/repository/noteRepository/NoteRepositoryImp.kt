package ru.netology.fmhandroid.repository.noteRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.netology.fmhandroid.dao.NoteDao
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.entity.*
import ru.netology.fmhandroid.exceptions.*

class NoteRepositoryImp(private val dao: NoteDao) : NoteRepository {
    override val data = dao.getAllNotes()
            .map(List<NoteEntity>::toDto)
            .flowOn(Dispatchers.Default)

    override suspend fun getAllNotes() {
        TODO("Not yet implemented")
    }

    override suspend fun saveNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(id: Long): Note {
        TODO("Not yet implemented")
    }
}