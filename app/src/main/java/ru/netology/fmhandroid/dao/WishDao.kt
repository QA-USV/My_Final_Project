package ru.netology.fmhandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.WishWithAllUsers
import ru.netology.fmhandroid.entity.WishEntity

@Dao
interface WishDao {
    @Query("SELECT * FROM WishEntity ORDER BY id DESC")
    fun getAllWishes(): Flow<List<WishWithAllUsers>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wish: WishEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wishes: List<WishEntity>)

    @Query("SELECT * FROM WishEntity WHERE id = :id")
    suspend fun getWishById(id: Int): WishEntity

    @Query("UPDATE WishEntity Set deleted = 1 WHERE id = :id")
    suspend fun deleteWishById(id: Int)
}