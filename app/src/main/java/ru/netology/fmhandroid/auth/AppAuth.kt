package ru.netology.fmhandroid.auth

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppAuth @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    private val accessTokenKey = "access"
    private val refreshTokenKey = "refresh"
    private var _accessToken = prefs.getString(accessTokenKey, null)
    val accessToken: String?
        get() = _accessToken
    private var _refreshToken = prefs.getString(refreshTokenKey, null)
    val refreshToken: String?
        get() = _accessToken

    fun saveTokens(authState: AuthState) {
        with(prefs.edit()) {
            putString(accessTokenKey, authState.accessToken)
            putString(refreshTokenKey, authState.refreshToken)
            apply()
        }
        _accessToken = authState.accessToken
        _refreshToken = authState.refreshToken
    }

    fun deleteTokens() {
        with(prefs.edit()) {
            clear()
            apply()
        }
        _accessToken = null
        _refreshToken = null
    }

    data class AuthState(
        val accessToken: String? = null,
        val refreshToken: String? = null
    )

    data class LoginData(
        val login: String,
        val password: String
    )
}