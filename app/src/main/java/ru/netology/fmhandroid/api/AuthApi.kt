package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.netology.fmhandroid.dto.AuthState
import ru.netology.fmhandroid.dto.LoginData

interface AuthApi {
    @POST("authentication/login")
    suspend fun getTokens(
        @Body loginData: LoginData
    ): Response<AuthState>
}
