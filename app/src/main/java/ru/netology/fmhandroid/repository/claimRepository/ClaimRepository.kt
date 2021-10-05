package ru.netology.fmhandroid.repository.claimRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.*

interface ClaimRepository {
    val data: Flow<List<ClaimWithCreatorAndExecutor>>
    val dataOpenInProgress: Flow<List<ClaimWithCreatorAndExecutor>>
    val dataComments: Flow<List<ClaimCommentWithCreator>>
    val dataClaim: Flow<ClaimWithCreatorAndExecutor>
    suspend fun getAllClaims(): List<Claim>
    suspend fun editClaim(editedClaim: Claim): Claim
    suspend fun saveClaim(claim: Claim): Claim
    suspend fun getClaimById(id: Int)
    suspend fun getAllCommentsForClaim(id: Int): List<ClaimComment>
    suspend fun saveClaimComment(claimId: Int, comment: ClaimComment): ClaimComment
    suspend fun changeClaimStatus(
        claimId: Int,
        newStatus: Claim.Status,
        claimExecutor: User?,
        claimComment: ClaimComment?
    ): Claim

    suspend fun changeClaimComment(comment: ClaimComment): ClaimComment
    suspend fun getClaimCommentById(id: Int): ClaimComment
    suspend fun getAllClaimsWithOpenAndInProgressStatus(): List<Claim>
}