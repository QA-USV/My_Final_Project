package ru.netology.fmhandroid.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment

interface ClaimApi {
    @GET("claims")
    suspend fun getAllClaims() : Response<List<Claim>>

    @GET("claims/open-in-progress")
    suspend fun getClaimsInOpenAndInProgressStatus() : Response<List<Claim>>

    @GET("claims/{id}")
    suspend fun getClaimById(@Path("id") id: Int): Response<Claim>

    @GET("claims/{id}/comments")
    suspend fun getAllClaimComments(@Path("id") id: Int): Response<List<ClaimComment>>

    @GET("claims/comments/{id}")
    suspend fun getClaimCommentById(@Path("id") id: Int): Response<ClaimComment>

    @POST("claims")
    suspend fun saveClaim(@Body claim: Claim): Response<Claim>

    @POST("claims/{id}/comments")
    suspend fun saveClaimComment(
        @Path("id") id: Int,
        @Body claimComment: ClaimComment
    ): Response<ClaimComment>

    @PUT("claims")
    suspend fun updateClaim(@Body claim: Claim): Response<Claim>

    @PUT("claims/{id}/status")
    suspend fun updateClaimStatus(
        @Path("id") id: Int,
        @Query("status") claimStatus: Claim.Status
    ): Response<Claim>

    @PUT("claims/comments")
    suspend fun updateClaimComment(@Body claimComment: ClaimComment): Response<ClaimComment>
}