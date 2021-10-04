package ru.netology.fmhandroid.repository.claimRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.ClaimApi
import ru.netology.fmhandroid.dao.ClaimDao
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.ClaimCommentWithCreator
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.utils.Utils.makeRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClaimRepositoryImpl @Inject constructor(
    private val claimApi: ClaimApi,
    private val claimDao: ClaimDao,
) : ClaimRepository {

    override val dataOpenInProgress: Flow<List<ClaimWithCreatorAndExecutor>>
        get() = claimDao.getClaimsOpenAndInProgressStatuses(
            Claim.Status.OPEN,
            Claim.Status.IN_PROGRESS
        ).flowOn(Dispatchers.Default)

    override val data: Flow<List<ClaimWithCreatorAndExecutor>>
        get() = claimDao.getAllClaims()
            .flowOn(Dispatchers.Default)

    override lateinit var dataComments: Flow<List<ClaimCommentWithCreator>>
    override lateinit var dataClaim: Flow<ClaimWithCreatorAndExecutor>

    override suspend fun getAllClaims(): List<Claim> {
        return makeRequest(
            request = { claimApi.getAllClaims() },
            onSuccess = { body ->
                claimDao.insertClaim(body.toEntity())
                body
            }
        )
    }

    override suspend fun editClaim(editedClaim: Claim): Claim = makeRequest(
        request = { claimApi.updateClaim(editedClaim) },
        onSuccess = { body ->
            claimDao.insertClaim(body.toEntity())
            dataClaim = dataClaim.map {
                ClaimWithCreatorAndExecutor(
                    claim = editedClaim,
                    executor = it.executor,
                    creator = it.creator
                )
            }
            body
        }
    )

    override suspend fun saveClaim(claim: Claim): Claim = makeRequest(
        request = { claimApi.saveClaim(claim) },
        onSuccess = { body ->
            claimDao.insertClaim(body.toEntity())
            body
        }
    )

    override suspend fun getClaimById(id: Int) {
        try {
            dataClaim = claimDao.getClaimById(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAllCommentsForClaim(id: Int): List<ClaimComment> = makeRequest(
        request = { claimApi.getAllClaimComments(id) },
        onSuccess = { body ->
            claimDao.insertComment(body.toEntity())
            dataComments = claimDao.getClaimComments(id).flowOn(Dispatchers.Default)
            body
        }
    )

    override suspend fun saveClaimComment(claimId: Int, comment: ClaimComment): ClaimComment =
        makeRequest(
            request = { claimApi.saveClaimComment(claimId, comment) },
            onSuccess = { body ->
                claimDao.insertComment(body.toEntity())
                dataComments.map { list ->
                    list.map {
                        if (it.claimComment.id != comment.id) {
                            it
                        } else {
                            it.claimComment.copy(description = comment.description)
                        }
                    }
                }
                body
            }
        )

    override suspend fun changeClaimStatus(claimId: Int, newStatus: Claim.Status): Claim =
        makeRequest(
            request = { claimApi.updateClaimStatus(claimId, newStatus) },
            onSuccess = { body ->
                claimDao.insertClaim(body.toEntity())
                dataClaim = dataClaim.map {
                    ClaimWithCreatorAndExecutor(
                        claim = it.claim.copy(status = newStatus),
                        executor = it.executor,
                        creator = it.creator
                    )
                }
                body
            }
        )

    override suspend fun changeClaimComment(comment: ClaimComment): ClaimComment = makeRequest(
        request = { claimApi.updateClaimComment(comment) },
        onSuccess = { body ->
            claimDao.insertComment(body.toEntity())
            dataComments.map { list ->
                list.map {
                    if (it.claimComment.id == comment.id) {
                        it.claimComment.copy(description = comment.description)
                    } else {
                        it
                    }
                }
            }
            body
        }
    )

    override suspend fun getClaimCommentById(id: Int): ClaimComment {
        TODO("Not yet implemented")
    }

    override suspend fun getAllClaimsWithOpenAndInProgressStatus(): List<Claim> {
        return makeRequest(
            request = { claimApi.getClaimsInOpenAndInProgressStatus() },
            onSuccess = { body ->
                claimDao.insertClaim(body.toEntity())
                body
            }
        )
    }
}