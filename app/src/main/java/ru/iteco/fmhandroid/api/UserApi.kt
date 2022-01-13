package ru.iteco.fmhandroid.api

import retrofit2.Response
import retrofit2.http.*
import ru.iteco.fmhandroid.dto.User

interface UserApi {
    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>

    @POST("authentication/userInfo")
    suspend fun getUserInfo(): Response<User>
}