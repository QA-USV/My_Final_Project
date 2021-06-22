package ru.netology.fmhandroid.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.db.AppDb
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Status
import ru.netology.fmhandroid.repository.noteRepository.NoteRepository
import ru.netology.fmhandroid.repository.noteRepository.NoteRepositoryImp
import ru.netology.fmhandroid.util.SingleLiveEvent

private val EMPTY_NOTE = Note(
    id = 0,
    patientId = 0,
    description = "",
    creatorId = 0,
    executorId = 0,
    createDate = "",
    planeExecuteDate = "",
    factExecuteDate = "",
    statusId = 0,
    status = Status.ACTIVE,
    comment = "",
    deleted = false
)

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository =
        NoteRepositoryImp(AppDb.getInstance(context = application).noteDao())

    val data: LiveData<List<Note>> = noteRepository.data
        .asLiveData(Dispatchers.Default)

    private val edited = MutableLiveData(EMPTY_NOTE)
    private val _noteCreatedEvent = SingleLiveEvent<Unit>()
    val noteCreatedEvent: LiveData<Unit>
        get() = _noteCreatedEvent

    init {
        loadNotesList()
    }

    fun loadNotesList() = viewModelScope.launch {
        try {
            noteRepository.getAllNotes()
        } catch (e: Exception) {
            Toast.makeText(getApplication(), R.string.error_loading, Toast.LENGTH_LONG).show()
        }
    }

    fun save() {
        val it = edited.value ?: return
        viewModelScope.launch {
            try {
                noteRepository.saveNote(it)
                loadNotesList()
            } catch (e: Exception) {
                Toast.makeText(getApplication(), R.string.error_saving, Toast.LENGTH_LONG).show()
            }
            _noteCreatedEvent.call()
        }
    }

    fun editNote(note: Note) {
        edited.value = note
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
        val it = edited.value ?: return
        viewModelScope.launch {
            try {
                noteRepository.saveNoteCommentById(it.id, it.comment)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setNoteStatusById() {
        val it = edited.value ?: return
        viewModelScope.launch {
            try {
                noteRepository.setNoteStatusById(it.id, it.status)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}