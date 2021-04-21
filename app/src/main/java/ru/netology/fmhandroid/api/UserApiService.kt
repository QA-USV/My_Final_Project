package ru.netology.fmhandroid.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.fmhandroid.BuildConfig
import ru.netology.fmhandroid.dto.User
import java.util.concurrent.TimeUnit

private const val BASE_URL = "${BuildConfig.BASE_URL}/fmh/"

private val loggin = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private val okhttp = OkHttpClient.Builder()
        .addInterceptor(loggin)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okhttp)
        .build()

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
        retrofit.create(UserApiService::class.java)
    }
}