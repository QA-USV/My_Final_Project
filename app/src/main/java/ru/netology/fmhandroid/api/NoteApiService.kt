package ru.netology.fmhandroid.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.fmhandroid.BuildConfig
import ru.netology.fmhandroid.dto.Note
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

interface NoteApiService {
    @GET("note")
    suspend fun getAllNotes(): Response<List<Note>>

    @POST("note")
    suspend fun saveNote(@Body note: Note): Response<Note>

    @PATCH("note")
    suspend fun updateNote(@Body note: Note): Response<Note>

    @GET("note/{id}")
    suspend fun getNoteById(@Path("id") id: Long): Response<Note>
}

object NoteApi {
    val service: NoteApiService by lazy {
        retrofit.create(NoteApiService::class.java)
    }
}