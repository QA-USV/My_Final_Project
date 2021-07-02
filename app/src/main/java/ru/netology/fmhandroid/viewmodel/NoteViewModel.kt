package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.db.AppDb
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.repository.noteRepository.NoteRepository
import ru.netology.fmhandroid.repository.noteRepository.NoteRepositoryImp
import ru.netology.fmhandroid.util.SingleLiveEvent
import javax.inject.Inject

private var emptyNote = Note()

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val data: Flow<List<Note>>
        get() = noteRepository.data

    private val _noteCreatedEvent = SingleLiveEvent<Unit>()
    val noteCreatedEvent: LiveData<Unit>
        get() = _noteCreatedEvent

    private val _loadNoteExceptionEvent = SingleLiveEvent<Unit>()
    val loadNoteExceptionEvent: LiveData<Unit>
        get() = _loadNoteExceptionEvent

    private val _saveNoteExceptionEvent = SingleLiveEvent<Unit>()
    val saveNoteExceptionEvent: LiveData<Unit>
        get() = _saveNoteExceptionEvent

    init {
        viewModelScope.launch {
            noteRepository.getAllNotes().collect()
        }
    }

    suspend fun getAllNotes() {
        viewModelScope.launch {
            try {
                noteRepository.getAllNotes()
            } catch (e: Exception) {
                e.printStackTrace()
                _noteCreatedEvent.call()
            }
        }
    }

    fun save() {
        emptyNote.let {
            viewModelScope.launch {
                try {
                    noteRepository.saveNote(it)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _saveNoteExceptionEvent.call()
                }
                _noteCreatedEvent.call()
            }
            emptyNote = Note()
        }
    }

    fun editNote(note: Note) {
        emptyNote = note
    }

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            try {
                noteRepository.getNoteById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveNoteCommentById() {
        emptyNote.let {
            viewModelScope.launch {
                try {
                    noteRepository.saveNoteCommentById(it.id, it.comment)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun setNoteStatusById() {
        emptyNote.let {
            viewModelScope.launch {
                try {
                    noteRepository.setNoteStatusById(it.id, it.status)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}