package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.adapter.OnClaimCommentItemClickListener
import ru.netology.fmhandroid.dto.*
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ClaimCardViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
) : ViewModel(), OnClaimCommentItemClickListener {
    private var claimId by Delegates.notNull<Int>()

    val dataFullClaim: Flow<FullClaim> by lazy {
        claimRepository.getClaimById(claimId)
    }

    // Временная переменная. После авторизации заменить на залогиненного юзера
    val user = User(
        id = 1,
        login = "User-1",
        password = "abcd",
        firstName = "Дмитрий",
        lastName = "Винокуров",
        middleName = "Владимирович",
        phoneNumber = "+79109008765",
        email = "Vinokurov@mail.ru"
    )

    val openClaimCommentEvent = MutableSharedFlow<ClaimCommentWithCreator>()
    val claimStatusChangedEvent = MutableSharedFlow<Unit>()
    val claimStatusChangeExceptionEvent = MutableSharedFlow<Unit>()
    val claimUpdateExceptionEvent = MutableSharedFlow<Unit>()
    val claimUpdatedEvent = MutableSharedFlow<Unit>()
    val claimCreatedEvent = MutableSharedFlow<Unit>()
    val createClaimExceptionEvent = MutableSharedFlow<Unit>()
    val claimCommentCreatedEvent = MutableSharedFlow<Unit>()
    val claimCommentUpdatedEvent = MutableSharedFlow<Unit>()
    val claimCommentCreateExceptionEvent = MutableSharedFlow<Unit>()
    val updateClaimCommentExceptionEvent = MutableSharedFlow<Unit>()
    val showNoCommentEditingRightsError = MutableSharedFlow<Unit>()

    fun createClaimComment(claimComment: ClaimComment) {
        viewModelScope.launch {
            try {
                claimRepository.saveClaimComment(claimComment.claimId, claimComment)
                claimCommentCreatedEvent.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                claimCommentCreateExceptionEvent.emit(Unit)
            }
        }
    }

    fun updateClaimComment(comment: ClaimComment) {
        viewModelScope.launch {
            try {
                claimRepository.changeClaimComment(comment)
                claimCommentUpdatedEvent.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                updateClaimCommentExceptionEvent.emit(Unit)
            }
        }
    }

    fun save(claim: Claim) {
        viewModelScope.launch {
            try {
                claimRepository.saveClaim(claim)
                claimCreatedEvent.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                createClaimExceptionEvent.emit(Unit)
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
            } catch (e: Exception) {
                e.printStackTrace()
                claimStatusChangeExceptionEvent.emit(Unit)
            }
        }
    }

    fun init(claimId: Int) {
        this.claimId = claimId
    }

    // region OnClaimCommentItemClickListener
    override fun onCard(claimComment: ClaimCommentWithCreator) {
        viewModelScope.launch {
            if (user.id == claimComment.creator.id) {
                openClaimCommentEvent.emit(claimComment)
            } else
                showNoCommentEditingRightsError.emit(Unit)
        }
    }
    // endregion OnClaimCommentItemClickListener
}
