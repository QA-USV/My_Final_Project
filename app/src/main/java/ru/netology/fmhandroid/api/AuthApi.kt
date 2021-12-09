package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import ru.netology.fmhandroid.auth.AppAuth


interface AuthApi {
    @POST("authentication/login")
    suspend fun getTokens(
        @Body loginData: AppAuth.LoginData
    ): Response<AppAuth.AuthState>

    @POST("authentication/refresh")
    suspend fun refreshTokens(
        @Query("token") refreshToken: String
    ): Response<AppAuth.AuthState>
}