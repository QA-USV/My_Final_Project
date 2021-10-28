package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
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

    val data: Flow<List<FullClaim>>
        get() = claimRepository.data

    val dataOpenInProgress: Flow<List<FullClaim>>
        get() = claimRepository.dataOpenInProgress

    init {
        viewModelScope.launch {
            claimRepository.getAllClaims()
        }
    }

    fun getAllClaims() {
        viewModelScope.launch {
            try {
                claimRepository.getAllClaims()
            } catch (e: Exception) {
                e.printStackTrace()
                claimsLoadException.emit(Unit)
            }
        }
    }
}
