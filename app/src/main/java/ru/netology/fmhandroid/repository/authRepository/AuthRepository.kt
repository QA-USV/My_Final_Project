package ru.netology.fmhandroid.repository.authRepository

import ru.netology.fmhandroid.dto.AuthState

interface AuthRepository {
    suspend fun login(login: String, password: String)
    suspend fun updateTokens(refreshToken: String): AuthState
}