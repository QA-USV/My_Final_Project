package ru.netology.fmhandroid.api

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.fmhandroid.BuildConfig.BASE_URL
import ru.netology.fmhandroid.dto.Admission
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.PatientStatusEnum
import java.util.concurrent.TimeUnit


interface PatientApi {
    @GET("patient?patients_status_list=ACTIVE")
    suspend fun getAllPatients(): Response<List<Patient>>

    @GET("patient")
    suspend fun getAllPatientsWithAdmissionStatus(
        @Query("patients_status_list") status: PatientStatusEnum
    ): Response<List<Patient>>

    @GET("patient/{id}")
    suspend fun getPatientById(@Path("id") id: Int): Response<Patient>

    @GET("patient/{id}/admission")
    suspend fun getPatientAdmissions(@Path("id") id: Int): Response<List<Admission>>

    @GET("patient/{id}/note")
    suspend fun getPatientNotes(@Path("id") id: Int): Response<List<Note>>

    @POST("patient/create")
    suspend fun savePatient(@Body patient: Patient): Response<Patient>

    @PATCH("patient/update")
    suspend fun updatePatient(@Body patient: Patient): Response<Patient>

    companion object {

        private val okhttp = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okhttp)
            .build()

        val service: PatientApi by lazy {
            retrofit.create(PatientApi::class.java)
        }
    }
}