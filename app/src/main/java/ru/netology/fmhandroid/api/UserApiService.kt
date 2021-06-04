package ru.netology.fmhandroid.api

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.fmhandroid.BuildConfig
import ru.netology.fmhandroid.dto.User
import java.util.concurrent.TimeUnit

interface UserApiService {
    @GET("user")
    suspend fun getAllUsers(): Response<List<User>>

    @POST("user")
    suspend fun saveUser(@Body user: User): Response<User>

    @PATCH("user")
    suspend fun updateUser(@Body user: User): Response<User>

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<User>


    companion object {

        private val okhttp = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttp)
            .build()

        val service: PatientApi by lazy {
            retrofit.create(PatientApi::class.java)
        }
    }
}
