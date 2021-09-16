package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor
import ru.netology.fmhandroid.model.ClaimCommentModel
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import ru.netology.fmhandroid.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class ClaimViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
) : ViewModel() {
    private var emptyClaim = Claim()

    private val _claimCreatedEvent = SingleLiveEvent<Unit>()
    val claimCreatedEvent: LiveData<Unit>
        get() = _claimCreatedEvent

    private val _claimCommentsLoadedEvent = SingleLiveEvent<Unit>()
    val claimCommentsLoadedEvent: LiveData<Unit>
        get() = _claimCommentsLoadedEvent

    private val _claimCommentsLoadExceptionEvent = SingleLiveEvent<Unit>()
    val claimCommentsLoadExceptionEvent: LiveData<Unit>
        get() = _claimCommentsLoadExceptionEvent

    private val _claimCommentUpdatedEvent = SingleLiveEvent<Unit>()
    val claimCommentUpdatedEvent: LiveData<Unit>
        get() = _claimCommentsLoadedEvent

    private val _updateClaimCommentExceptionEvent = SingleLiveEvent<Unit>()
    val updateClaimCommentExceptionEvent: LiveData<Unit>
        get() = _loadClaimExceptionEvent

    private val _loadClaimExceptionEvent = SingleLiveEvent<Unit>()
    val loadClaimExceptionEvent: LiveData<Unit>
        get() = _loadClaimExceptionEvent

    private val _saveClaimExceptionEvent = SingleLiveEvent<Unit>()
    val saveClaimExceptionEvent: LiveData<Unit>
        get() = _saveClaimExceptionEvent

    val data: Flow<List<ClaimWithCreatorAndExecutor>>
        // Обсудить где лучше трансформировать дату!!!
        get() = claimRepository.data.transform { list ->
            // Изменить параметры сортировки в соответствии с ТЗ!
            this.emit(list.sortedWith(compareBy({ it.executor?.lastName }, { it.claim.title })))
        }

    lateinit var commentsData: LiveData<ClaimCommentModel>

    init {
        viewModelScope.launch {
            claimRepository.getAllClaims()
        }
    }

    fun save() {
        emptyClaim.let {
            viewModelScope.launch {
                try {
                    claimRepository.saveClaim(it)
                    emptyClaim = Claim()
                    _claimCreatedEvent.call()
                } catch (e: Exception) {
                    e.printStackTrace()
                    _saveClaimExceptionEvent.call()
                }
            }
        }
    }

    fun updateClaimComment(comment: ClaimComment) {
        viewModelScope.launch {
            try {
                claimRepository.changeClaimComment(comment)
                _claimCommentUpdatedEvent.call()
            } catch (e: Exception) {
                e.printStackTrace()
                _updateClaimCommentExceptionEvent.call()
            }
        }

    }

    fun changeClaimData(
        title: String,
        executor: String,
        planExecuteDate: String,
        planExecuteTime: String,
        description: String
    ) {
        emptyClaim = emptyClaim.copy(
            title = title.trim(),
            executorId = executor.toInt(), // временно
            planExecuteDate = "$planExecuteDate-$planExecuteTime",
            description = description.trim()
        )
    }

    fun getAllClaimComments(id: Int) {
        viewModelScope.launch {
            try {
                claimRepository.getAllCommentsForClaim(id)
                _claimCommentsLoadedEvent.call()
                commentsData = claimRepository.dataComments.map(::ClaimCommentModel)
                    .asLiveData(Dispatchers.Default)
            } catch (e: Exception) {
                e.printStackTrace()
                _claimCommentsLoadExceptionEvent.call()
            }
        }
    }
}