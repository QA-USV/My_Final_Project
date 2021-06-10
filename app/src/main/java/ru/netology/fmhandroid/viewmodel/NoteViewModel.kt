package ru.netology.fmhandroid.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.db.AppDb
import ru.netology.fmhandroid.dto.PatientStatusEnum
import ru.netology.fmhandroid.model.FeedModelState
import ru.netology.fmhandroid.model.NoteModel
import ru.netology.fmhandroid.model.PatientModel
import ru.netology.fmhandroid.repository.PatientRepositoryImpl
import ru.netology.fmhandroid.repository.noteRepository.NoteRepository
import ru.netology.fmhandroid.repository.noteRepository.NoteRepositoryImp
import ru.netology.fmhandroid.repository.patientRepository.PatientRepository
import ru.netology.fmhandroid.utils.SingleLiveEvent
import ru.netology.fmhandroid.utils.Utils

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository =
        NoteRepositoryImp(
            AppDb.getInstance(context = application).noteDao()
        )

    val data: LiveData<NoteModel> = noteRepository.data
        .map(::NoteModel)
        .asLiveData(Dispatchers.Default)

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState
    private val edited = MutableLiveData(Utils.emptyNote)
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
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(errorLoading = true)
        }
    }

    suspend fun getAllNotes() {
        TODO("Not yet implemented")
    }

    suspend fun saveNote() {
        TODO("Not yet implemented")
    }

    suspend fun updateNote() {
        TODO("Not yet implemented")
    }

    suspend fun getNoteById() {
        TODO("Not yet implemented")
    }
}