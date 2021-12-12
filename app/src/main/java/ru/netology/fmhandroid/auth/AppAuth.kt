package ru.netology.fmhandroid.auth

import android.content.Context
import android.content.SharedPreferences
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
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    private val accessTokenKey = "access"
    private val refreshTokenKey = "refresh"
    var accessToken = prefs.getString(accessTokenKey, null)
        private set
    var refreshToken = prefs.getString(refreshTokenKey, null)
        private set

    fun saveTokens(authState: AuthState) {
        with(prefs.edit()) {
            putString(accessTokenKey, authState.accessToken)
            putString(refreshTokenKey, authState.refreshToken)
            apply()
        }
        accessToken = authState.accessToken
        refreshToken = authState.refreshToken
    }

    fun deleteTokens() {
        with(prefs.edit()) {
            clear()
            apply()
        }
        accessToken = null
        refreshToken = null
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