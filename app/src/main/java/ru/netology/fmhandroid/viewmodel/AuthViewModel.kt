package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.auth.AppAuth
import ru.netology.fmhandroid.repository.authRepository.AuthRepository
import ru.netology.fmhandroid.repository.userRepository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val appAuth: AppAuth
) : ViewModel() {

    val nonAuthorizedEvent = MutableSharedFlow<Unit>()
    val authorizedEvent = MutableSharedFlow<Unit>()
    val loginExceptionEvent = MutableSharedFlow<Unit>()
    val loginEvent = MutableSharedFlow<Unit>()
    val getUserListExceptionEvent = MutableSharedFlow<Unit>()
    val userListLoadedEvent = MutableSharedFlow<Unit>()

//    private val _serverErrorStateFlow: MutableStateFlow<AppAuth.ServerErrorState> = MutableStateFlow(
//        AppAuth.ServerErrorState()
//    )
    val serverErrorStateFlow = appAuth.serverErrorStateFlow

    fun login(login: String, password: String) {
        viewModelScope.launch {
            try {
                authRepository.login(login, password)
                userRepository.getUserInfo()
                loginEvent.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                loginExceptionEvent.emit(Unit)
            }
        }
    }

    fun authorization() {
        viewModelScope.launch {
            val authState = appAuth.authState
            if (authState == null) {
                nonAuthorizedEvent.emit(Unit)
            } else {
                userRepository.getUserInfo()
                authorizedEvent.emit(Unit)
            }
        }
    }

    fun logOut() {
        appAuth.authState = null
        userRepository.userLogOut()
    }

    suspend fun loadUserList() {
        try {
            userRepository.getAllUsers()
            userListLoadedEvent.emit(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            getUserListExceptionEvent.emit(Unit)
        }
    }
}