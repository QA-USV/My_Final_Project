package ru.netology.fmhandroid.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.viewModels
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.netology.fmhandroid.dto.AuthState
import ru.netology.fmhandroid.viewmodel.AuthViewModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class AppAuth @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var _noValidTokenEvent = MutableSharedFlow<Unit>()
    val noValidTokenEvent = _noValidTokenEvent

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    private val accessTokenKey = "access"
    private val refreshTokenKey = "refresh"

    /**
     * Набор токенов. `null` если пользователь не авторизован
     */
    var authState by Delegates.observable(
        createInitialAuthState()
    )
    { _, _, authState ->
        with(prefs.edit()) {
            putString(accessTokenKey, authState?.accessToken)
            putString(refreshTokenKey, authState?.refreshToken)
            apply()
        }
    }

    fun noValidTokens() {

    }


    private fun createInitialAuthState(): AuthState? {
        val accessToken1 = prefs.getString(accessTokenKey, null)
        return if (accessToken1 == null) null else {
            val refreshToken1 = prefs.getString(refreshTokenKey, null)
            checkNotNull(refreshToken1) { "accessToken и refreshToken должны быть не null" }
            AuthState(
                accessToken = accessToken1,
                refreshToken = refreshToken1
            )
        }
    }
}