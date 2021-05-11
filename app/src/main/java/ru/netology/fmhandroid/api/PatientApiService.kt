package ru.netology.fmhandroid.api

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.fmhandroid.BuildConfig.BASE_URL
import ru.netology.fmhandroid.dto.Admission
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Patient
import java.util.concurrent.TimeUnit


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
    @GET("patient/{id}")
    suspend fun getPatientById(@Path("id") id: Int): Response<Patient>

    @GET("patient/{id}/admission")
    suspend fun getPatientAdmissions(@Path("id") id: Int): Response<List<Admission>>

    @GET("patient/{id}/note")
    suspend fun getPatientNotes(@Path("id") id: Int): Response<List<Note>>

    @POST("patient")
    suspend fun savePatient(@Body patient: Patient): Response<Patient>

    @PATCH("patient")
    suspend fun editPatient(@Body patient: Patient): Response<Patient>

    object PatientApi {
        val service: PatientApiService by lazy {
            retrofit.create(PatientApiService::class.java)
        }
    }
}