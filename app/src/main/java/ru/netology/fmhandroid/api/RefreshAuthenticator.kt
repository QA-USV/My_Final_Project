package ru.netology.fmhandroid.api

import android.util.JsonReader
import kotlinx.coroutines.runBlocking
import okhttp3.*
import ru.netology.fmhandroid.auth.AppAuth
import ru.netology.fmhandroid.repository.authRepository.AuthRepository
import javax.inject.Provider
import com.google.gson.Gson




private const val invalidLoginToken = "ERR_INVALID_LOGIN"
private const val invalidRefreshToken = "ERR_INVALID_REFRESH"

class RefreshAuthenticator(
    private val authRepositoryProvider: Provider<AuthRepository>,
    private val appAuth: AppAuth
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        synchronized(this) {
            val authRepository = authRepositoryProvider.get()
            val refreshToken = checkNotNull(appAuth.authState) {
                "user must be authorized"
            }.refreshToken
            val accessToken = runBlocking {
                authRepository.updateTokens(refreshToken)
            }.accessToken
            return response.request.newBuilder()
                .header("Authorization", accessToken)
                .build()
        }
    }
}