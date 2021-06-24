package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.fmhandroid.api.RetrofitBuilder.getRetrofit
import ru.netology.fmhandroid.dto.Admission
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.PatientStatusEnum


interface PatientApiService {
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
}

object PatientApi {
    val service: PatientApiService by lazy {
        getRetrofit.create(PatientApiService::class.java)
    }
}
