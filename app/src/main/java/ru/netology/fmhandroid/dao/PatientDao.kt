package ru.netology.fmhandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.entity.AdmissionEntity
import ru.netology.fmhandroid.entity.NoteEntity
import ru.netology.fmhandroid.entity.PatientEntity

@Dao
interface PatientDao {
    @Query("SELECT * FROM PatientEntity ORDER BY id DESC")
    fun getAllPatients(): Flow<List<PatientEntity>>

    @Query("SELECT * FROM PatientEntity WHERE id = :id")
    suspend fun getPatientById(id: Int): PatientEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: PatientEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patients: List<PatientEntity>)

    @Query("SELECT * FROM NoteEntity WHERE patientId = :id")
    suspend fun getNotesByPatientId(id: Int): List<NoteEntity>

    @Query("SELECT * FROM AdmissionEntity WHERE patientId == :id")
    suspend fun getAdmissionsByPatientId(id: Int): List<AdmissionEntity>

    @Query("UPDATE PatientEntity SET deleted = 1 WHERE id = :id")
    suspend fun deletePatientById(id: Int)
}
