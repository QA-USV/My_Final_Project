package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.netology.fmhandroid.dto.*
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import ru.netology.fmhandroid.utils.Events
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ClaimViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
) : ViewModel() {

    lateinit var commentsData: Flow<List<ClaimCommentWithCreator>>

    val claimCreatedEvent = Events()
    val claimCommentCreatedEvent = Events()
    val claimCommentsLoadedEvent = Events()
    val claimCommentUpdatedEvent = Events()
    val claimStatusChangedEvent = Events()
    val claimUpdatedEvent = Events()
    val claimCommentsLoadExceptionEvent = Events()
    val claimCommentCreateExceptionEvent = Events()
    val updateClaimCommentExceptionEvent = Events()
    val claimStatusChangeExceptionEvent = Events()
    val loadClaimExceptionEvent = Events()
    val createClaimExceptionEvent = Events()
    val claimUpdateExceptionEvent = Events()

    val data: Flow<List<ClaimWithCreatorAndExecutor>>
        get() = claimRepository.data

    val dataOpenInProgress: Flow<List<ClaimWithCreatorAndExecutor>>
        get() = claimRepository.dataOpenInProgress

    init {
        viewModelScope.launch {
            claimRepository.getAllClaims()
        }
    }

    fun save(claim: Claim) {
        viewModelScope.launch {
            try {
                claimRepository.saveClaim(claim)
                Events.produceEvents(claimCreatedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(createClaimExceptionEvent)
            }
        }
    }

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

    fun updateClaim(claim: Claim) {
        viewModelScope.launch {
            try {
                claimRepository.editClaim(claim)
                Events.produceEvents(claimUpdatedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(claimUpdateExceptionEvent)
            }
        }
    }

    fun getAllClaimComments(id: Int) {
        viewModelScope.launch {
            try {
                claimRepository.getAllCommentsForClaim(id)
                commentsData = claimRepository.dataComments.map { it }
                Events.produceEvents(claimCommentsLoadedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(claimCommentsLoadExceptionEvent)
            }
        }
    }

    suspend fun changeClaimStatus(claimId: Int, newClaimStatus: Claim.Status): Boolean {

        return claimRepository.changeClaimStatus(claimId, newClaimStatus)
    }
}