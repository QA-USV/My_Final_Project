package ru.netology.fmhandroid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.netology.fmhandroid.dao.*
import ru.netology.fmhandroid.entity.*

@Database(
    entities = [
        UserEntity::class,
        PatientEntity::class,
        WishEntity::class,
        AdmissionEntity::class,
        ClaimEntity::class,
        ClaimCommentEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(LocalDateTimeConverters::class, ClaimStatusConverter::class)
abstract class AppDb : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun patientDao(): PatientDao
    abstract fun wishDao(): WishDao
    abstract fun admissionDao(): AdmissionDao
    abstract fun claimDao(): ClaimDao
}
