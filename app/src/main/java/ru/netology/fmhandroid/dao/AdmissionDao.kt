package ru.netology.fmhandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.fmhandroid.entity.AdmissionEntity

@Dao
interface AdmissionDao {

    @Query("SELECT * FROM AdmissionEntity WHERE id = :id")
    suspend fun getAdmissionById(id: Long): AdmissionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(admission: AdmissionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(admissions: List<AdmissionEntity>)
}