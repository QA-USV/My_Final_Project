package ru.netology.fmhandroid.repository.claimRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.*

interface ClaimRepository {
    fun getClaimsByStatus(
        coroutineScope: CoroutineScope,
        vararg statuses: Claim.Status
    ): Flow<List<FullClaim>>
//    val dataOpenInProgress: Flow<List<FullClaim>>
    suspend fun refreshClaims()
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