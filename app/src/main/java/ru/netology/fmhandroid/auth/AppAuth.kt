package ru.netology.fmhandroid.auth

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.netology.fmhandroid.dto.AuthState
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

private const val responseMessage = "ERR_INVALID_REFRESH"

@Singleton
class AppAuth @Inject constructor(
    @ApplicationContext private val context: Context
) {
//    private val _noValidTokenEvent = MutableSharedFlow<Unit>()
//    val noValidTokenEvent = _noValidTokenEvent.asSharedFlow()

    private val _serverErrorStateFlow: MutableStateFlow<Unit> = MutableStateFlow(Unit
    )
    val serverErrorStateFlow = _serverErrorStateFlow.asStateFlow()

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

    fun createEventFromServerError(
//        response: Response
    ) {
//        val responseCode = response.code
//        val responseMessage = response.message
        _serverErrorStateFlow.value = Unit
//            ServerErrorState(responseCode = responseCode, responseMessage = responseMessage)
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

//    data class ServerErrorState(val responseCode: Int = 0, val responseMessage: String = "")
}