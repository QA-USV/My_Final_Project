package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.netology.fmhandroid.dto.Patient

private val retrofit = RetrofitBuilder().retrofit

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