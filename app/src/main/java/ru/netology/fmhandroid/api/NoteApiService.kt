package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.fmhandroid.dto.Note

private val retrofit = RetrofitBuilder().retrofit

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