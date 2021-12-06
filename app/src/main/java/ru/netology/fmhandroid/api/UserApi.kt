package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.fmhandroid.dto.User

interface UserApi {
    @GET("user")
    suspend fun getAllUsers(): Response<List<User>>

    @POST("authentication/userInfo")
    fun getUserInfo(): Response<User>
}