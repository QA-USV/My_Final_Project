package ru.netology.fmhandroid.repository.claimRepository

import android.database.Observable
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor

interface ClaimRepository {
    val data: Flow<List<ClaimWithCreatorAndExecutor>>
    val dataComments: Flow<List<ClaimComment>>
    suspend fun getAllClaims(): List<Claim>
    suspend fun editClaim(claim: Claim): Claim
    suspend fun saveClaim(claim: Claim): Claim
    suspend fun getClaimById(id: Int): Claim
    suspend fun getAllCommentsForClaim(id: Int): List<ClaimComment>
    suspend fun saveClaimComment(claimId: Int, comment: ClaimComment): ClaimComment
    suspend fun changeClaimStatus(claimId: Int, newStatus: Claim.Status): Claim
    suspend fun changeClaimComment(comment: ClaimComment): ClaimComment
    suspend fun getClaimCommentById(id: Int): ClaimComment
    suspend fun getAllClaimsWithOpenAndInProgressStatus(): Flow<List<Claim>>
}