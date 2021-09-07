package ru.netology.fmhandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.entity.ClaimEntity
import ru.netology.fmhandroid.entity.WishEntity

@Dao
interface ClaimDao {
    @Query("SELECT * FROM ClaimEntity ORDER BY id DESC")
    fun getAllClaims(): Flow<List<ClaimEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(claim: ClaimEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(claims: List<ClaimEntity>)

    @Query("SELECT * FROM ClaimEntity WHERE id = :id")
    suspend fun getClaimById(id: Int): ClaimEntity

    @Query("UPDATE ClaimEntity Set deleted = 1 WHERE id = :id")
    suspend fun deleteClaimById(id: Int)
}
