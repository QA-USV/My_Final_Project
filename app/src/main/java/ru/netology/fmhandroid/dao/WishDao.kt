package ru.netology.fmhandroid.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.entity.WishEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Dao
interface WishDao {
    @Query("SELECT * FROM WishEntity ORDER BY id DESC")
    fun getAllWishes(): Flow<List<WishEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wish: WishEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wishes: List<WishEntity>)

    @Query("SELECT * FROM WishEntity WHERE id = :id")
    suspend fun getWishById(id: Int): WishEntity

    @Query("UPDATE WishEntity Set deleted = 1 WHERE id = :id")
    suspend fun deleteWishById(id: Int)
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
    }
}
