package ru.netology.fmhandroid.dto

/**
 * Госпитализация
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
     * Фактическая дата госпитализации
     */
    val factDateIn: String,
    /**
     * Фактическая дата выписки
     */
    val factDateOut: String,
    /**
     * Идентификатор статуса госпитализации
     */
    val statusId: Int,
    val roomId: Int,
    val comment: String,
    val deleted: Boolean = false,
)
