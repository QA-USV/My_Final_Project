package ru.netology.fmhandroid.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.netology.fmhandroid.BuildConfig
import ru.netology.fmhandroid.dto.Patient
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

interface PatientApiService {
    @GET("patient")
    suspend fun getAllPatients(): Response<List<Patient>>

    @POST("patient")
    suspend fun savePatient(@Body patient: Patient): Response<Patient>
}

object PatientApi {
    val service: PatientApiService by lazy {
        retrofit.create(PatientApiService::class.java)
    }
}