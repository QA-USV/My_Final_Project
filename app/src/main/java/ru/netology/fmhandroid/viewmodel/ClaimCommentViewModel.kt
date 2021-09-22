package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.internal.Factory
import ru.netology.fmhandroid.repository.claimRepository.ClaimRepository


class ClaimCommentViewModel
@AssistedInject
constructor(repository: ClaimRepository, @Assisted private val claimId: Int) :
    ViewModel() {
//    init {
//        repository.claimId = claimId
//    }
//
//    @AssistedInject.Factory
//    interface AssistedFactory {
//        fun create(claimId: Int): ClaimCommentViewModel
//    }
//
//    companion object {
//        fun provideFactory(
//            assistedFactory: AssistedFactory,
//            claimId: Int
//        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                return assistedFactory.create(claimId) as T
//            }
//        }
//    }
}