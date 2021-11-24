package ru.netology.fmhandroid.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.FullClaim
import ru.netology.fmhandroid.entity.ClaimEntity

@Dao
interface ClaimDao {

    @Query(
        "SELECT * FROM ClaimEntity ORDER BY planExecuteDate ASC, createDate DESC"
    )
    fun getAllClaims(): Flow<List<FullClaim>>

    @Transaction
    @Query(
        """
       SELECT * FROM ClaimEntity
       WHERE (status IN (:listStatuses))
       ORDER BY planExecuteDate ASC, createDate DESC
    """
    )
    fun getClaimsByStatus(
        listStatuses: List<Claim.Status>
    ): Flow<List<FullClaim>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClaim(claim: ClaimEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClaim(claims: List<ClaimEntity>)

    @Query("SELECT * FROM ClaimEntity WHERE id = :id")
    fun getClaimById(id: Int): Flow<FullClaim>
}

class ClaimClaimStatusConverter {

    @TypeConverter
    fun toClaimStatus(status: String): Claim.Status = Claim.Status.valueOf(status)

    @TypeConverter
    fun fromClaimStatus(status: Claim.Status) = status.name
}
