package ru.netology.fmhandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity ORDER BY id DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun getUserById(id: Long): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<UserEntity>)
}