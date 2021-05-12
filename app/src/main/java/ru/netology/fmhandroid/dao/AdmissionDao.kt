package ru.netology.fmhandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.Admission
import ru.netology.fmhandroid.entity.AdmissionEntity


@Dao
interface AdmissionDao {
    @Query(
        "SELECT * FROM AdmissionEntity WHERE patientId = :id AND deleted = 0 ORDER BY id DESC"
    )
    fun getAllAdmissionsByPatientId(id: Int): Flow<List<AdmissionEntity>>

    @Query("SELECT * FROM AdmissionEntity WHERE id = :id")
    suspend fun getAdmissionById(id: Int): AdmissionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(admission: AdmissionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(admission: List<AdmissionEntity>)

    @Query("UPDATE AdmissionEntity Set deleted = 1 WHERE id = :id")
    suspend fun deleteAdmissionById(id: Int)
}
