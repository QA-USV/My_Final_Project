package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Note.Status

interface NoteApi {
    @GET("note")
    suspend fun getAllNotes(): Response<List<Note>>

    @POST("note")
    suspend fun saveNote(@Body note: Note): Response<Note>

    @PATCH("note")
    suspend fun editNote(@Body note: Note): Response<Note>

    @GET("note/{id}")
    suspend fun getNoteById(@Path("id") id: Int): Response<Note>

    @POST("note/comment/{noteId}")
    suspend fun saveNoteCommentById(
        @Path("noteId") noteId: Int,
        @Query("comment") comment: String
    ): Response<Note>

    @POST("note/status/{noteId}")
    suspend fun setNoteStatusById(
        @Path("noteId") noteId: Int,
        @Query("status") status: Status
    ): Response<Note>
}