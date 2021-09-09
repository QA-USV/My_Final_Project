package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class ClaimViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
): ViewModel() {

    val data: Flow<List<Claim.ClaimWithCreatorAndExecutor>>
        get() = claimRepository.data


    init {
        viewModelScope.launch {
            claimRepository.getAllClaims()
        }
    }
}