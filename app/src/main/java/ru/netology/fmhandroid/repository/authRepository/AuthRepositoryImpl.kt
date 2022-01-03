package ru.netology.fmhandroid.repository.authRepository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.fmhandroid.api.AuthApi
import ru.netology.fmhandroid.api.RefreshTokensApi
import ru.netology.fmhandroid.auth.AppAuth
import ru.netology.fmhandroid.dto.AuthState
import ru.netology.fmhandroid.dto.JwtResponse
import ru.netology.fmhandroid.dto.LoginData
import ru.netology.fmhandroid.dto.RefreshRequest
import ru.netology.fmhandroid.exceptions.ApiException
import ru.netology.fmhandroid.exceptions.AuthorizationException
import ru.netology.fmhandroid.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val refreshTokensApi: RefreshTokensApi,
    private val appAuth: AppAuth
) : AuthRepository {

    override suspend fun login(login: String, password: String) =
        Utils.makeRequest(
            request = { authApi.getTokens(LoginData(login = login, password = password)) },
            onSuccess = { body -> appAuth.authState = body },
            onFailure = {
                // Было бы здорово вынести этот код в отдельную функцию.
                val gson = Gson()
                val type = object : TypeToken<JwtResponse>() {}.type
                val errorResponse: JwtResponse? = gson.fromJson(it.errorBody()?.charStream(), type)
                if (errorResponse?.message.equals("ERR_INVALID_LOGIN")) {
                    throw AuthorizationException
                }
            }
        )

    override suspend fun updateTokens(refreshToken: String): AuthState? =
        Utils.makeRequest(
            request = {
                refreshTokensApi.refreshTokens(
                    refreshToken,
                    RefreshRequest(refreshToken)
                )
            },
            onSuccess = { body ->
                appAuth.authState = body
                body
            },
            onFailure = {
                if (it.code() == 401) {
                    appAuth.authState = null
                    null
                } else {
                    throw ApiException(it.code(), it.message())
                }
            }
        )
}