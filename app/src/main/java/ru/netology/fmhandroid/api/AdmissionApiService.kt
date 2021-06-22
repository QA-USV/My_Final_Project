package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.fmhandroid.api.RetrofitBuilder.Companion.retrofit
import ru.netology.fmhandroid.dto.Admission

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