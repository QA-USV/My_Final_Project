package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import javax.inject.Inject

@HiltViewModel
class ClaimViewModel @Inject constructor(
    val claimRepository: ClaimRepository
): ViewModel() {

    val data: Flow<List<Claim>>
        get() = claimRepository.data

}