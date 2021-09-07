package ru.netology.fmhandroid.dto

/**
 * Госпитализация
 */
data class Admission(
    val id: Int? = null,
    val patientId: Int? = null,
    /**
     * Дата залпанированной госпитализации
     */
    val planDateIn: String? = null,
    /**
     * Дата запланированной выписки
     */
    val planDateOut: String? = null,
    /**
     * Фактическая дата госпитализации
     */
    val factDateIn: String? = null,
    /**
     * Фактическая дата выписки
     */
    val factDateOut: String? = null,
    /**
     * Идентификатор статуса госпитализации
     */
    val statusId: Int? = null,
    val roomId: Int? = null,
    val comment: String? = null,
    val deleted: Boolean = false,
)
