package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.netology.fmhandroid.dto.AuthState
import ru.netology.fmhandroid.dto.RefreshRequest

interface RefreshTokensApi {
    @POST("authentication/refresh")
    suspend fun refreshTokens(
        @Header("Authorization") refreshToken: String,
        @Body refreshRequest: RefreshRequest
    ): Response<AuthState>
}