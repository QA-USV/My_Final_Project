package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.FullClaim
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import javax.inject.Inject

@HiltViewModel
class ClaimViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
) : ViewModel() {

    val claimsLoadException = MutableSharedFlow<Unit>()

    val statusesFlow = MutableStateFlow(
        listOf(
            Claim.Status.OPEN,
            Claim.Status.IN_PROGRESS
        )
    )

    val data: Flow<List<FullClaim>> = statusesFlow.flatMapConcat { statuses ->
        claimRepository.getClaimsByStatus(
            viewModelScope,
            statuses
        )
    }

    fun onFilterClaimsMenuItemClicked(vararg statuses: Claim.Status) {


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
}
