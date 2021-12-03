package ru.netology.fmhandroid.auth

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppAuth @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val accessTokenKey = "access"
    val refreshTokenKey = "refresh"

    private val _authStateFlow: MutableStateFlow<AuthState>

    init {
        val access = prefs.getString("access", null)
        val refresh = prefs.getString("refresh", null)

        if (access == null) {
            _authStateFlow = MutableStateFlow(AuthState())
            with(prefs.edit()) {
                clear()
                apply()
            }
        } else {
            _authStateFlow = MutableStateFlow(AuthState(access, refresh))
        }
    }

    val authStateFlow: StateFlow<AuthState> = _authStateFlow.asStateFlow()

    fun setAuth(authState: AuthState) {
        _authStateFlow.value = AuthState(authState.accessToken, authState.refreshToken)
        with(prefs.edit()) {
            putString(accessTokenKey, authState.accessToken)
            putString(refreshTokenKey, authState.refreshToken)
            apply()
        }
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