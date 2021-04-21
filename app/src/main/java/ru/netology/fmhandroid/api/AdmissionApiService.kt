package ru.netology.fmhandroid.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.fmhandroid.BuildConfig
import ru.netology.fmhandroid.dto.Admission
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

interface AdmissionApiService {

    @POST("admission")
    suspend fun saveAdmission(@Body admission: Admission): Response<Admission>

    @PATCH("admission")
    suspend fun updateAdmission(@Body admission: Admission): Response<Admission>

    @GET("patient/{id}")
    suspend fun getAdmissionById(@Path("id") id: Long): Response<Admission>
}

object AdmissionApi {
    val service: AdmissionApiService by lazy {
        retrofit.create(AdmissionApiService::class.java)
    }
}