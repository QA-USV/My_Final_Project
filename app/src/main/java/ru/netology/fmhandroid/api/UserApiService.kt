package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import ru.netology.fmhandroid.api.RetrofitBuilder.Companion.rtf
import ru.netology.fmhandroid.dto.User

interface UserApiService {
    @GET("user")
    suspend fun getAllUsers(): Response<List<User>>

    @POST("user")
    suspend fun saveUser(@Body user: User): Response<User>

    @PATCH("user")
    suspend fun updateUser(@Body user: User): Response<User>

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<User>
}

object UserApi {
    val service: UserApiService by lazy {
        rtf.create(UserApiService::class.java)
    }
}