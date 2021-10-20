package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.FullClaim
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import ru.netology.fmhandroid.utils.Events
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ClaimCardViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
): ViewModel() {
    private var claimId by Delegates.notNull<Int>()

    val dataFullClaim: Flow<FullClaim> by lazy {
        claimRepository.getClaimById(claimId)
    }

    val claimStatusChangedEvent = MutableSharedFlow<Unit>()
    val claimStatusChangeExceptionEvent = MutableSharedFlow<Unit>()
    val claimUpdateExceptionEvent = MutableSharedFlow<Unit>()

    val claimCommentsLoadExceptionEvent = Events()
    val claimCommentCreateExceptionEvent = Events()
    val updateClaimCommentExceptionEvent = Events()
    val loadClaimExceptionEvent = Events()
    val claimLoadedEvent = Events()
    val claimCommentCreatedEvent = Events()
    val claimCommentsLoadedEvent = Events()
    val claimCommentUpdatedEvent = Events()
    val claimUpdatedEvent = MutableSharedFlow<Unit>()

    fun createClaimComment(claimComment: ClaimComment) {
        viewModelScope.launch {
            try {
                claimComment.claimId?.let { claimRepository.saveClaimComment(it, claimComment) }
                Events.produceEvents(claimCommentCreatedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(claimCommentCreateExceptionEvent)
            }
        }
    }

    fun updateClaimComment(comment: ClaimComment) {
        viewModelScope.launch {
            try {
                claimRepository.changeClaimComment(comment)
                Events.produceEvents(claimCommentUpdatedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(updateClaimCommentExceptionEvent)
            }
        }
    }

    fun updateClaim(updatedClaim: Claim) {
        viewModelScope.launch {
            try {
                claimRepository.editClaim(updatedClaim)
                claimUpdatedEvent.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                claimUpdateExceptionEvent.emit(Unit)
            }
        }
    }

    fun getAllClaimComments(id: Int) {
        viewModelScope.launch {
            try {
                claimRepository.getAllCommentsForClaim(id)
//                commentsData = claimRepository.dataComments
                Events.produceEvents(claimCommentsLoadedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(claimCommentsLoadExceptionEvent)
            }
        }
    }

    fun getClaimById(claimId: Int) {
        viewModelScope.launch {
            try {
                claimRepository.getClaimById(claimId)
                Events.produceEvents(claimLoadedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(loadClaimExceptionEvent)
            }
        }
    }

    fun changeClaimStatus(
        claimId: Int,
        newClaimStatus: Claim.Status,
        executorId: Int?,
        claimComment: ClaimComment
    ) {
        viewModelScope.launch {
            try {
                claimRepository.changeClaimStatus(
                    claimId,
                    newClaimStatus,
                    executorId,
                    claimComment
                )

                claimStatusChangedEvent.emit(Unit)
//                if (!claimComment.description.isNullOrBlank()) {
//                    commentsData = claimRepository.dataComments
//                }
            } catch (e: Exception) {
                e.printStackTrace()
                claimStatusChangeExceptionEvent.emit(Unit)
            }
        }
    }

    fun init(claimId: Int) {
        this.claimId = claimId
    }

}