package ru.netology.fmhandroid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.netology.fmhandroid.dao.*
import ru.netology.fmhandroid.entity.AdmissionEntity
import ru.netology.fmhandroid.entity.NoteEntity
import ru.netology.fmhandroid.entity.PatientEntity
import ru.netology.fmhandroid.entity.UserEntity

@Database(
    entities = [UserEntity::class, PatientEntity::class, NoteEntity::class, AdmissionEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDb: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun patientDao(): PatientDao
    abstract fun noteDao(): NoteDao
    abstract fun admissionDao(): AdmissionDao
}
