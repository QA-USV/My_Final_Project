package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.auth.AppAuth
import ru.netology.fmhandroid.repository.authRepository.AuthRepository
import ru.netology.fmhandroid.repository.userRepository.UserRepository
import ru.netology.fmhandroid.utils.Utils
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val appAuth: AppAuth
) : ViewModel() {
    val currentUser = Utils.Empty.emptyUser
    val nonAuthorizedEvent = MutableSharedFlow<Unit>()

    fun authorization() {
        viewModelScope.launch {
            if (appAuth.accessToken == null) {
                nonAuthorizedEvent.emit(Unit)
            } else {
                with(userRepository.getUserInfo()) {
                    currentUser.copy(
                        id = this.id,
                        admin = this.admin,
                        firstName = this.firstName,
                        lastName = this.lastName,
                        middleName = this.middleName
                    )
                }
            }
        }

    }
}