package ru.netology.fmhandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import ru.netology.fmhandroid.dto.*
import ru.netology.fmhandroid.entity.ClaimCommentEntity
import ru.netology.fmhandroid.entity.WishCommentEntity
import ru.netology.fmhandroid.entity.WishEntity

@Dao
interface WishDao {
    @Query("SELECT * FROM WishEntity ORDER BY id DESC")
    fun getAllWishes(): Flow<List<WishWithAllUsers>>

    @Query("SELECT * FROM WishCommentEntity WHERE wishId = :wishId")
    fun getWishComments(wishId: Int): Flow<List<WishCommentWithCreator>>

    @Query("SELECT * FROM WishEntity WHERE status LIKE :firstStatus OR status LIKE :secondStatus ORDER BY planExecuteDate ASC, createDate DESC")
    fun getWishesOpenAndInProgressStatuses(
        firstStatus: Wish.Status,
        secondStatus: Wish.Status
    ): Flow<List<WishWithAllUsers>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wish: WishEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wishes: List<WishEntity>)

    @Query("SELECT * FROM WishEntity WHERE id = :id")
    suspend fun getWishById(id: Int): WishEntity

    @Query("UPDATE WishEntity Set deleted = 1 WHERE id = :id")
    suspend fun deleteWishById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comments: List<WishCommentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: WishCommentEntity)

}