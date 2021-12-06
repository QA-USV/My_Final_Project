package ru.netology.fmhandroid.repository.authRepository

import ru.netology.fmhandroid.api.AuthApi
import ru.netology.fmhandroid.auth.AppAuth
import ru.netology.fmhandroid.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val appAuth: AppAuth
) : AuthRepository {
    override suspend fun login(login: String, password: String) =
        Utils.makeRequest(
            request = { authApi.getTokens(AppAuth.LoginData(login = login, password = password)) },
            onSuccess = { body -> appAuth.saveTokens(body) }
        )

    override suspend fun updateTokens(refreshToken: String) =
        Utils.makeRequest(
            request = { authApi.refreshTokens(refreshToken) },
            onSuccess = { body -> appAuth.saveTokens(body) }
        )
}