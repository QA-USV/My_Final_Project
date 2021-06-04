package ru.netology.fmhandroid.api

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.fmhandroid.BuildConfig
import ru.netology.fmhandroid.dto.Note
import java.util.concurrent.TimeUnit

interface NoteApiService {
    @GET("note")
    suspend fun getAllNotes(): Response<List<Note>>

    @POST("note")
    suspend fun saveNote(@Body note: Note): Response<Note>

    @PATCH("note")
    suspend fun updateNote(@Body note: Note): Response<Note>

    @GET("note/{id}")
    suspend fun getNoteById(@Path("id") id: Long): Response<Note>

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
