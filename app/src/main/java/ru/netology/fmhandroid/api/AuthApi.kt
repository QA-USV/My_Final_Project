package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import ru.netology.fmhandroid.dto.AuthState
import ru.netology.fmhandroid.dto.LoginData
import ru.netology.fmhandroid.dto.RefreshRequest


interface AuthApi {
    @POST("authentication/login")
    suspend fun getTokens(
        @Body loginData: LoginData
    ): Response<AuthState>

    @POST("authentication/refresh")
    suspend fun refreshTokens(
        @Body refreshRequest: RefreshRequest
    ): Response<AuthState>
}