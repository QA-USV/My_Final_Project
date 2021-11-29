package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.adapter.OnClaimItemClickListener
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.FullClaim
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import javax.inject.Inject

@HiltViewModel
class ClaimViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
) : ViewModel(), OnClaimItemClickListener {

    val claimsLoadException = MutableSharedFlow<Unit>()
    private val claimCommentsLoadedEvent = MutableSharedFlow<Unit>()
    val claimCommentsLoadExceptionEvent = MutableSharedFlow<Unit>()
    val openClaimEvent = MutableSharedFlow<FullClaim>()

    val statusesFlow = MutableStateFlow(
        listOf(
            Claim.Status.OPEN,
            Claim.Status.IN_PROGRESS
        )
    )

    val data: Flow<List<FullClaim>> = statusesFlow.flatMapLatest { statuses ->
        internalOnRefresh()
        claimRepository.getClaimsByStatus(
            viewModelScope,
            statuses
        )
    }

    fun onFilterClaimsMenuItemClicked(statuses: List<Claim.Status>) {
        viewModelScope.launch {
            statusesFlow.emit(statuses)
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            internalOnRefresh()
        }
    }

    private suspend fun internalOnRefresh() {
        try {
            claimRepository.refreshClaims()
        } catch (e: Exception) {
            e.printStackTrace()
            claimsLoadException.emit(Unit)
        }
    }

    override fun onCard(fullClaim: FullClaim) {
        viewModelScope.launch {
            try {
                fullClaim.claim.id?.let { claimRepository.getAllCommentsForClaim(it) }
                claimCommentsLoadedEvent.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                claimCommentsLoadExceptionEvent.emit(Unit)
            }
            openClaimEvent.emit(fullClaim)
        }
    }
}
