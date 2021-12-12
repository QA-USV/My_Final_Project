package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.auth.AppAuth
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.exceptions.ApiException
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

//    val currentUser: User
//        get() = userRepository.currentUser
//
//    val userList: List<User>
//        get() = userRepository.userList

    val nonAuthorizedEvent = MutableSharedFlow<Unit>()
    val authorizedEvent = MutableSharedFlow<Unit>()
    val loginExceptionEvent = MutableSharedFlow<Unit>()
    val loginEvent = MutableSharedFlow<Unit>()
    val getUserInfoExceptionEvent = MutableSharedFlow<Unit>()
    val getUserListExceptionEvent = MutableSharedFlow<Unit>()
    val userListLoadedEvent = MutableSharedFlow<Unit>()

    fun login(login: String, password: String) {
        viewModelScope.launch {
            try {
                authRepository.login(login, password)
//                getUserInfo()
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
                try {

                    userRepository.getUserInfo()
                    authorizedEvent.emit(Unit)
                } catch (e: ApiException) {

                    e.printStackTrace()
                    authRepository.updateTokens(
                        authState.refreshToken
                    )
//                    getUserInfo()
                    authorizedEvent.emit(Unit)
                } finally {

                }
            }
        }
    }

    fun logOut() {
        appAuth.authState = null
//        currentUser = Utils.Empty.emptyUser
    }

    suspend fun loadUserList() {
        try {
//            userList = userRepository.getAllUsers()
            userListLoadedEvent.emit(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            getUserListExceptionEvent.emit(Unit)
        }
    }
}