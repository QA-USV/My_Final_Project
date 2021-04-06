package ru.netology.fmhandroid.dto

import java.util.*

data class Note(
    val id: Long,
    val patient_id: Long,
    var description: Char,
    val creator_id: Long,
    val executor_id: Long,
    val create_date: Date,
    val plane_execute_date: Date,
    val fact_execute_date: Date,
    val status_id: Long,
    var comment: Char,
    val deleted: Boolean
)