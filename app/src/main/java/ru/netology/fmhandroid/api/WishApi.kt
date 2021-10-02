package ru.netology.fmhandroid.api


import retrofit2.Response
import retrofit2.http.*
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.Wish
import ru.netology.fmhandroid.dto.Wish.Status
import ru.netology.fmhandroid.dto.WishComment

interface WishApi {
    @GET("wishes")
    suspend fun getAllWishes(): Response<List<Wish>>

    @GET("wishes/{id}/comments")
    suspend fun getAllWishComments(@Path("id") id: Int): Response<List<WishComment>>

    @POST("wishes")
    suspend fun saveWish(@Body wish: Wish): Response<Wish>

    @PATCH("wishes")
    suspend fun editWish(@Body wish: Wish): Response<Wish>

    @GET("wishes/{id}")
    suspend fun getWishById(@Path("id") id: Int): Response<Wish>

    // Исправить
    @POST("wishes/comments/{wishId}")
    suspend fun saveWishCommentById(
        @Path("wishId") wishId: Int,
        @Query("comments") comment: String
    ): Response<Wish>

    // Исправить
    @POST("wishes/status/{wishId}")
    suspend fun setWishStatusById(
        @Path("wishId") wishId: Int,
        @Query("status") status: Status
    ): Response<Wish>

    @POST("wishes/{id}/comments")
    suspend fun saveWishComment(
        @Path("id") id: Int,
        @Body wishComment: WishComment
    ): Response<WishComment>

    @PUT("wishes/comments")
    suspend fun updateWishComment(@Body wishComment: WishComment): Response<WishComment>
}