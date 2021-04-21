package ru.netology.fmhandroid.dto

data class Note(
    val id: Long,
    val patientId: Long,
    var description: String,
    val creatorId: Long,
    val executorId: Long,
    val createDate: String,
    val planeExecuteDate: String,
    val factExecuteDate: String,
    val statusId: Long,
    var comment: String,
    val deleted: Boolean
)