package ru.netology.fmhandroid.repository.claimRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.ClaimApi
import ru.netology.fmhandroid.dao.ClaimDao
import ru.netology.fmhandroid.dao.UserDao
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor
import ru.netology.fmhandroid.entity.ClaimCommentEntity
import ru.netology.fmhandroid.entity.toDto
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.utils.Utils.makeRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClaimRepositoryImpl @Inject constructor(
    private val claimApi: ClaimApi,
    private val claimDao: ClaimDao,
    private val userDao: UserDao
) : ClaimRepository {

    override val data: Flow<List<ClaimWithCreatorAndExecutor>>
        get() = claimDao.getAllClaims()
            .flowOn(Dispatchers.Default)

    override lateinit var dataComments: Flow<List<ClaimComment>>


    override suspend fun getAllClaims(): List<Claim> {
        return makeRequest(
            request = { claimApi.getAllClaims() },
            onSuccess = { body ->
                claimDao.insertClaim(body.toEntity())
                body
            }
        )
    }

    override suspend fun editClaim(claim: Claim): Claim {
        TODO("Not yet implemented")
    }

    override suspend fun saveClaim(claim: Claim): Claim = makeRequest(
        request = { claimApi.saveClaim(claim) },
        onSuccess = { body ->
            claimDao.insertClaim(body.toEntity())
            body
        }
    )

    override suspend fun getClaimById(id: Int): Claim {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCommentsForClaim(id: Int): List<ClaimComment> = makeRequest(
        request = { claimApi.getAllClaimComments(id) },
        onSuccess = { body ->
            claimDao.insertComment(body.toEntity())
            dataComments = claimDao.getClaimComments(id).map(List<ClaimCommentEntity>::toDto).flowOn(Dispatchers.Default)
            body
        }
    )

    override suspend fun saveClaimComment(claimId: Int, comment: ClaimComment): ClaimComment {
        TODO("Not yet implemented")
    }

    override suspend fun changeClaimStatus(claimId: Int, newStatus: Claim.Status): Claim {
        TODO("Not yet implemented")
    }

    override suspend fun changeClaimComment(comment: ClaimComment): ClaimComment = makeRequest(
        request = { claimApi.updateClaimComment(comment)},
        onSuccess = { body ->
            claimDao.insertComment(body.toEntity())
            dataComments.map { list ->
                list.map {
                    if (it.id == comment.id) it.copy(description = comment.description) else it
                }
            }
            body
        }
    )

    override suspend fun getClaimCommentById(id: Int): ClaimComment {
        TODO("Not yet implemented")
    }

    override suspend fun getAllClaimsWithOpenAndInProgressStatus(): Flow<List<Claim>> {
        TODO("Not yet implemented")
    }
}