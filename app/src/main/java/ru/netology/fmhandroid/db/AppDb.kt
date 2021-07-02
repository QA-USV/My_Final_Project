package ru.netology.fmhandroid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.netology.fmhandroid.dao.AdmissionDao
import ru.netology.fmhandroid.dao.NoteDao
import ru.netology.fmhandroid.dao.PatientDao
import ru.netology.fmhandroid.dao.UserDao
import ru.netology.fmhandroid.entity.AdmissionEntity
import ru.netology.fmhandroid.entity.NoteEntity
import ru.netology.fmhandroid.entity.PatientEntity
import ru.netology.fmhandroid.entity.UserEntity

@Database(
    entities = [UserEntity::class, PatientEntity::class, NoteEntity::class, AdmissionEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDb: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun patientDao(): PatientDao
    abstract fun noteDao(): NoteDao
    abstract fun admissionDao(): AdmissionDao
}
