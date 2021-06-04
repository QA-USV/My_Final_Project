package ru.netology.fmhandroid.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.db.AppDb
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.NoteStatusEnum
import ru.netology.fmhandroid.model.FeedModel
import ru.netology.fmhandroid.model.FeedModelState
import ru.netology.fmhandroid.repository.noteRepository.NoteRepository
import ru.netology.fmhandroid.repository.noteRepository.NoteRepositoryImp
import ru.netology.fmhandroid.util.SingleLiveEvent

val emptyNote = Note(
    id = 0,
    patientId = 0,
    description = "",
    creatorId = 0,
    executorId = 0,
    createDate = "",
    planeExecuteDate = "",
    factExecuteDate = "",
    statusId = 0,
    status = NoteStatusEnum.ACTIVE,
    comment = "",
    deleted = false
)

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository =
        NoteRepositoryImp(AppDb.getInstance(context = application).noteDao())

    val data: LiveData<FeedModel> = noteRepository.data
        .map(::FeedModel)
        .asLiveData(Dispatchers.Default)

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState
    private val edited = MutableLiveData(emptyNote)
    private val _noteCreated = SingleLiveEvent<Unit>()
    val noteCreated: LiveData<Unit>
        get() = _noteCreated

    init {
        loadNotesList()
    }

    fun loadNotesList() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(loading = true)
            noteRepository.getAllNotes()
//            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(errorLoading = true)
        }
    }

    fun save() {
        edited.value?.let {
            viewModelScope.launch {
                try {
                    noteRepository.saveNote(it)
                    _dataState.value = FeedModelState()
                    loadNotesList()
                } catch (e: Exception) {
//                        _dataState.value = FeedModelState(errorSaving = true)
                }
                _noteCreated.value = Unit
            }
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
        edited.value?.let {
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
        edited.value?.let {
            viewModelScope.launch {
                try {
                    noteRepository.setNoteStatusById(it.id, it.status)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun changeNoteData() {
        TODO("Not yet implemented")
    }
}