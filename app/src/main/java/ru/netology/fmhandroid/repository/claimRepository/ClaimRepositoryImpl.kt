package ru.netology.fmhandroid.repository.claimRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.ClaimStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClaimRepositoryImpl @Inject constructor() : ClaimRepository {

    override val data: Flow<List<Claim>> = TODO()

    override suspend fun getAllClaims(): Flow<List<Claim>> {
        TODO("Not yet implemented")
    }

    override suspend fun editClaim(claim: Claim): Claim {
        TODO("Not yet implemented")
    }

    override suspend fun saveClaim(claim: Claim): Claim {
        TODO("Not yet implemented")
    }

    override suspend fun getClaimById(id: Int): Claim {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCommentsForClaim(id: Int): List<ClaimComment> {
        TODO("Not yet implemented")
    }

    override suspend fun saveClaimComment(claimId: Int, comment: ClaimComment): Int {
        TODO("Not yet implemented")
    }

    override suspend fun changeClaimStatus(claimId: Int, newStatus: ClaimStatus): Claim {
        TODO("Not yet implemented")
    }

    override suspend fun changeClaimComment() {
        TODO("Not yet implemented")
    }

    override suspend fun getClaimCommentById(id: Int): ClaimComment {
        TODO("Not yet implemented")
    }

    override suspend fun getAllClaimsWithOpenAndInProgressStatus(): Flow<List<Claim>> {
        TODO("Not yet implemented")
    }
}