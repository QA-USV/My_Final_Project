package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository
import javax.inject.Inject

@HiltViewModel
class ClaimViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
): ViewModel() {

    val data: Flow<List<ClaimWithCreatorAndExecutor>>
    // Обсудить где лучше трансформировать дату!!!
        get() = claimRepository.data.transform { list ->
            // Изменить параметры сортировки в соответствии с ТЗ!
            this.emit(list.sortedWith(compareBy({it.executor.lastName}, {it.claim.title})))
        }

    init {
        viewModelScope.launch {
            claimRepository.getAllClaims()
        }
    }
}