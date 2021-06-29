package ru.netology.fmhandroid.repository.noteRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.NoteApi
import ru.netology.fmhandroid.dao.NoteDao
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Note.Status
import ru.netology.fmhandroid.entity.toDto
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.util.makeRequest

class NoteRepositoryImp(private val noteDao: NoteDao) : NoteRepository {

    override val data: Flow<List<Note>>
        get() = noteDao.getAllNotes()
            .map{it.toDto()}
            .flowOn(Dispatchers.Default)

    override suspend fun getAllNotes(): Flow<List<Note>> = flow {
        makeRequest(
            request = { NoteApi.service.getAllNotes() },
            onSuccess = { body ->
                noteDao.insert(body.toEntity())
                emit(body)
            }
        )
    }

    override suspend fun saveNote(note: Note): Note = makeRequest(
        request = { NoteApi.service.saveNote(note) },
        onSuccess = { body ->
            noteDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun editNote(note: Note) = makeRequest(
        request = { NoteApi.service.editNote(note) },
        onSuccess = { body ->
            noteDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun getNoteById(id: Int): Note = makeRequest(
        request = { NoteApi.service.getNoteById(id) },
        onSuccess = { body ->
            noteDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun saveNoteCommentById(noteId: Int, comment: String): Note = makeRequest(
        request = { NoteApi.service.saveNoteCommentById(noteId, comment) },
        onSuccess = { body ->
            noteDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun setNoteStatusById(noteId: Int, status: Status): Note = makeRequest(
        request = { NoteApi.service.setNoteStatusById(noteId, status)},
        onSuccess = { body ->
            noteDao.insert(body.toEntity())
            body
        }
    )
}