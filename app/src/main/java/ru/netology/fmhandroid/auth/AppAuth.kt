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
    private val accessTokenKey = "access"
    private val refreshTokenKey = "refresh"
    val accessToken = prefs.getString(accessTokenKey, null)
    val refreshToken = prefs.getString(refreshTokenKey, null)

//    init {
//        val accessToken = prefs.getString("access", null)
//        val refreshToken = prefs.getString("refresh", null)
//    }

    fun saveTokens(authState: AuthState) {
        with(prefs.edit()) {
            putString(accessTokenKey, authState.accessToken)
            putString(refreshTokenKey, authState.refreshToken)
            apply()
        }
    }

    fun deleteTokens() {
        with(prefs.edit()) {
            clear()
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