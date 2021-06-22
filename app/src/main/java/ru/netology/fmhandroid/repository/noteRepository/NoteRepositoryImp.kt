package ru.netology.fmhandroid.repository.noteRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.netology.fmhandroid.api.NoteApi
import ru.netology.fmhandroid.dao.NoteDao
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Status
import ru.netology.fmhandroid.entity.*
import ru.netology.fmhandroid.util.makeRequest

class NoteRepositoryImp(private val dao: NoteDao) : NoteRepository {
    override val data = dao.getAllNotes()
        .map(List<NoteEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun getAllNotes(): List<Note> = makeRequest(
        request = { NoteApi.service.getAllNotes() },
        onSuccess = { body ->
            dao.insert(body.toEntity())
            body
        }
    )

    override suspend fun saveNote(note: Note): Note = makeRequest(
        request = { NoteApi.service.saveNote(note) },
        onSuccess = { body ->
            dao.insert(body.toEntity())
            body
        }
    )

    override suspend fun editNote(note: Note) = makeRequest(
        request = { NoteApi.service.editNote(note) },
        onSuccess = { body ->
            dao.insert(body.toEntity())
            body
        }
    )

    override suspend fun getNoteById(id: Int): Note = makeRequest(
        request = { NoteApi.service.getNoteById(id) },
        onSuccess = { body ->
            dao.insert(body.toEntity())
            body
        }
    )

    override suspend fun saveNoteCommentById(noteId: Int, comment: String): Note = makeRequest(
        request = { NoteApi.service.saveNoteCommentById(noteId, comment) },
        onSuccess = { body ->
            dao.insert(body.toEntity())
            body
        }
    )

    override suspend fun setNoteStatusById(noteId: Int, status: Status): Note = makeRequest(
        request = { NoteApi.service.setNoteStatusById(noteId, status)},
        onSuccess = { body ->
            dao.insert(body.toEntity())
            body
        }
    )
}