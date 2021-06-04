package ru.netology.fmhandroid.api

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.fmhandroid.BuildConfig
import ru.netology.fmhandroid.dto.Admission
import java.util.concurrent.TimeUnit

interface AdmissionApiService {

    @POST("admission")
    suspend fun saveAdmission(@Body admission: Admission): Response<Admission>

    @PATCH("admission")
    suspend fun updateAdmission(@Body admission: Admission): Response<Admission>

    @GET("patient/{id}")
    suspend fun getAdmissionById(@Path("id") id: Long): Response<Admission>

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
