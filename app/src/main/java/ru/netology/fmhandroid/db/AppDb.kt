package ru.netology.fmhandroid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.netology.fmhandroid.dao.*
import ru.netology.fmhandroid.entity.*

@Database(
    entities = [
        UserEntity::class,
        ClaimEntity::class,
        ClaimCommentEntity::class,
        NewsEntity::class,
        NewsCategoryEntity::class,
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    ClaimClaimStatusConverter::class
)
abstract class AppDb : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getClaimDao(): ClaimDao
    abstract fun getClaimCommentDao(): ClaimCommentDao
    abstract fun getNewsDao(): NewsDao
    abstract fun getNewsCategoryDao(): NewsCategoryDao
}
