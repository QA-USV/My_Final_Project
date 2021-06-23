package ru.netology.fmhandroid.repository.noteRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.netology.fmhandroid.api.NoteApi
import ru.netology.fmhandroid.dao.NoteDao
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.NoteStatus
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.util.makeRequest

class NoteRepositoryImp(private val dao: NoteDao) : NoteRepository {

    override suspend fun getAllNotes(): Flow<List<Note>> = flow {
        makeRequest(
            request = { NoteApi.service.getAllNotes() },
            onSuccess = { body ->
                dao.insert(body.toEntity())
                emit(body)
            }
        )
    }

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

    override suspend fun setNoteStatusById(noteId: Int, status: NoteStatus): Note = makeRequest(
        request = { NoteApi.service.setNoteStatusById(noteId, status)},
        onSuccess = { body ->
            dao.insert(body.toEntity())
            body
        }
    )
}