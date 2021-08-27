package ru.netology.fmhandroid.repository.claimRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.ClaimStatus

interface ClaimRepository {
    val data: Flow<List<Claim>>
    suspend fun getAllClaims(): Flow<List<Claim>>
    suspend fun editClaim(claim: Claim): Claim
    suspend fun saveClaim(claim: Claim): Claim
    suspend fun getClaimById(id: Int): Claim
    suspend fun getAllCommentsForClaim(id: Int): List<ClaimComment>
    suspend fun saveClaimComment(claimId: Int, comment: ClaimComment): Int
    suspend fun changeClaimStatus(claimId: Int, newStatus: ClaimStatus): Claim
    // Вопрос по возвращаемому значению.
    suspend fun changeClaimComment()
    suspend fun getClaimCommentById(id: Int): ClaimComment
    suspend fun getAllClaimsWithOpenAndInProgressStatus(): Flow<List<Claim>>
}