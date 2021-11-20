package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.adapter.OnClaimItemClickListener
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.FullClaim
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import ru.netology.fmhandroid.ui.ClaimListFragmentDirections
import javax.inject.Inject

@HiltViewModel
class ClaimViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
) : ViewModel(), OnClaimItemClickListener {

    val claimsLoadException = MutableSharedFlow<Unit>()
    val claimCommentsLoadedEvent = MutableSharedFlow<Unit>()
    val claimCommentsLoadExceptionEvent = MutableSharedFlow<Unit>()
    val opeClaimEvent = MutableSharedFlow<FullClaim>()

    val statusesFlow = MutableStateFlow(
        listOf(
            Claim.Status.OPEN,
            Claim.Status.IN_PROGRESS
        )
    )

    val data: Flow<List<FullClaim>> = statusesFlow.flatMapMerge { statuses ->
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
            try {
                claimRepository.refreshClaims()
            } catch (e: Exception) {
                e.printStackTrace()
                claimsLoadException.emit(Unit)
            }
        }
    }

    fun getAllClaimComments(id: Int) {
        viewModelScope.launch {
            try {
                claimRepository.getAllCommentsForClaim(id)
                claimCommentsLoadedEvent.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                claimCommentsLoadExceptionEvent.emit(Unit)
            }
        }
    }

    override fun onCard(fullClaim: FullClaim) {
        fullClaim.claim.id?.let { getAllClaimComments(it) }
        viewModelScope.launch {
            claimCommentsLoadedEvent.collect {
                opeClaimEvent.emit(fullClaim)
            }
        }
    }
}

