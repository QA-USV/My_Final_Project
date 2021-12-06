package ru.netology.fmhandroid.repository.authRepository

interface AuthRepository {
    suspend fun login(login: String, password: String)
    suspend fun updateTokens(refreshToken: String)
}