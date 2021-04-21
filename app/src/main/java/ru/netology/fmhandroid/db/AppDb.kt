package ru.netology.fmhandroid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.netology.fmhandroid.dao.AdmissionDao
import ru.netology.fmhandroid.dao.NoteDao
import ru.netology.fmhandroid.dao.PatientDao
import ru.netology.fmhandroid.dao.UserDao
import ru.netology.fmhandroid.dto.Admission
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

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDb::class.java, "app.db")
                .build()
    }
}