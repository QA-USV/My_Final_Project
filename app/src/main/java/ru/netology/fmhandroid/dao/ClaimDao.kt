package ru.netology.fmhandroid.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimCommentWithCreator
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor
import ru.netology.fmhandroid.entity.ClaimCommentEntity
import ru.netology.fmhandroid.entity.ClaimEntity

@Dao
interface ClaimDao {
    // Сортируй тут по датам дурачок :)
    @Query(
        "SELECT * FROM ClaimEntity ORDER BY planExecuteDate ASC, createDate DESC"
    )
    fun getAllClaims(): Flow<List<ClaimWithCreatorAndExecutor>>

    @Query("SELECT * FROM ClaimEntity WHERE status LIKE :firstStatus OR status LIKE :secondStatus ORDER BY planExecuteDate ASC, createDate DESC")
    fun getClaimsOpenAndInProgressStatuses(
        firstStatus: Claim.Status,
        secondStatus: Claim.Status
    ): Flow<List<ClaimWithCreatorAndExecutor>>

    @Query("SELECT * FROM ClaimCommentEntity WHERE claimId = :claimId")
    fun getClaimComments(claimId: Int): Flow<List<ClaimCommentWithCreator>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClaim(claim: ClaimEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comments: List<ClaimCommentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: ClaimCommentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClaim(claims: List<ClaimEntity>)

    @Query("SELECT * FROM ClaimEntity WHERE id = :id")
    suspend fun getClaimById(id: Int): ClaimEntity

    @Query("UPDATE ClaimEntity Set deleted = 1 WHERE id = :id")
    suspend fun deleteClaimById(id: Int)
}

class WishClaimStatusConverter {

    @TypeConverter
    fun toClaimStatus(status: String): Claim.Status = Claim.Status.valueOf(status)

    @TypeConverter
    fun fromClaimStatus(status: Claim.Status) = status.name
}
