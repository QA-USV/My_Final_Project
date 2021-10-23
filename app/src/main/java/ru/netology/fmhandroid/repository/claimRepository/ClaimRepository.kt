package ru.netology.fmhandroid.repository.claimRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.*

interface ClaimRepository {
    val data: Flow<List<FullClaim>>
    val dataOpenInProgress: Flow<List<FullClaim>>
    suspend fun getAllClaims(): List<Claim>
    suspend fun editClaim(editedClaim: Claim): Claim
    suspend fun saveClaim(claim: Claim): Claim
    fun getClaimById(id: Int): Flow<FullClaim>
    suspend fun getAllCommentsForClaim(id: Int): List<ClaimComment>
    suspend fun saveClaimComment(claimId: Int, comment: ClaimComment): ClaimComment
    suspend fun changeClaimStatus(
        claimId: Int,
        newStatus: Claim.Status,
        executorId: Int?,
        claimComment: ClaimComment
    ): Claim
    suspend fun changeClaimComment(comment: ClaimComment): ClaimComment
    suspend fun getAllClaimsWithOpenAndInProgressStatus(): List<Claim>
}