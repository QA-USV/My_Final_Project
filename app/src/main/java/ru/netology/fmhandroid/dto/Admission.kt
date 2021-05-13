package ru.netology.fmhandroid.dto

/**
 * Госпитацизация
 */
data class Admission(
    val id: Int,
    val patientId: Int,
    /**
     * Дата залпанированной госпитализации
     */
    val planDateIn: String,
    /**
     * Дата запланированной выписки
     */
    val planDateOut: String,
    /**
     * Вактическая дата госпитализации
     */
    val factDateIn: String,
    /**
     * Фактическая дата выписки
     */
    val factDateOut: String,
    /**
     * Актуальный статус госпитализации
     */
    val admStatusId: Int,
    val roomId: Int,
    val comment: String,
    val deleted: Boolean = false,
)
