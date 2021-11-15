package ru.netology.fmhandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.ClaimCommentWithCreator
import ru.netology.fmhandroid.entity.ClaimCommentEntity

@Dao
interface ClaimCommentDao {
    @Query("SELECT * FROM ClaimCommentEntity WHERE claimId = :claimId")
    fun getClaimComments(claimId: Int): Flow<List<ClaimCommentWithCreator>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comments: List<ClaimCommentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: ClaimCommentEntity)
}
