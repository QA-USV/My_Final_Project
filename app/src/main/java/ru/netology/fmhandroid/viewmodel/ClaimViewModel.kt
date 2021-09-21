package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.ClaimCommentWithCreator
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import ru.netology.fmhandroid.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class ClaimViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
) : ViewModel() {

    lateinit var commentsData: Flow<List<ClaimCommentWithCreator>>
    private var emptyClaim = Claim()

    private val _claimCreatedEvent = SingleLiveEvent<Unit>()
    val claimCreatedEvent: LiveData<Unit>
        get() = _claimCreatedEvent

    private val _claimCommentCreatedEvent = SingleLiveEvent<Unit>()
    val claimCommentCreatedEvent: LiveData<Unit>
        get() = _claimCreatedEvent

    private val _claimCommentCreateExceptionEvent = SingleLiveEvent<Unit>()
    val claimCommentCreateExceptionEvent: LiveData<Unit>
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

    private val _claimStatusChangedEvent = SingleLiveEvent<Unit>()
    val claimStatusChangedEvent: LiveData<Unit>
        get() = _claimStatusChangedEvent

    private val _claimStatusChangeException = SingleLiveEvent<Unit>()
    val claimStatusChangeException: LiveData<Unit>
        get() = _claimStatusChangeException

    private val _loadClaimExceptionEvent = SingleLiveEvent<Unit>()
    val loadClaimExceptionEvent: LiveData<Unit>
        get() = _loadClaimExceptionEvent

    private val _createClaimExceptionEvent = SingleLiveEvent<Unit>()
    val createClaimExceptionEvent: LiveData<Unit>
        get() = _createClaimExceptionEvent

    private val _claimUpdatedEvent = SingleLiveEvent<Unit>()
    val claimUpdatedEvent: LiveData<Unit>
        get() = _claimUpdatedEvent

    private val _claimUpdateExceptionEvent = SingleLiveEvent<Unit>()
    val claimUpdateExceptionEvent: LiveData<Unit>
    get() = _claimUpdateExceptionEvent

    val data: Flow<List<ClaimWithCreatorAndExecutor>>
        get() = claimRepository.data

    val dataOpenInProgress: Flow<List<ClaimWithCreatorAndExecutor>>
        get() = claimRepository.dataOpenInProgress

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
                    _createClaimExceptionEvent.call()
                }
            }
        }
    }

    fun createClaimComment(claimComment: ClaimComment) {
        viewModelScope.launch {
            try {
                claimComment.claimId?.let { claimRepository.saveClaimComment(it, claimComment) }
                _claimCommentCreatedEvent.call()
            } catch (e: Exception) {
                e.printStackTrace()
                _claimCommentCreateExceptionEvent.call()
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

    fun updateClaim(claim: Claim) {
        viewModelScope.launch {
            try {
                claimRepository.editClaim(claim)
                _claimUpdatedEvent.call()
            } catch (e: Exception) {
                e.printStackTrace()
                _claimUpdateExceptionEvent.call()
            }
        }
    }

    fun getAllClaimComments(id: Int) {
        viewModelScope.launch {
            try {
                claimRepository.getAllCommentsForClaim(id)
                commentsData = claimRepository.dataComments.map { it }
                println(commentsData)
                _claimCommentsLoadedEvent.call()
            } catch (e: Exception) {
                e.printStackTrace()
                _claimCommentsLoadExceptionEvent.call()
            }
        }
    }

    fun changeClaimStatus(claimId: Int, newClaimStatus: Claim.Status) {
        viewModelScope.launch {
            try {
                claimRepository.changeClaimStatus(claimId, newClaimStatus)
                _claimStatusChangedEvent.call()
            } catch (e: Exception) {
                e.printStackTrace()
                _claimStatusChangeException.call()
            }
        }
    }
}