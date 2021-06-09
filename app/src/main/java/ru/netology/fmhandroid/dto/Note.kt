package ru.netology.fmhandroid.dto

data class Note(
    val id: Int,
    val patientId: Int,
    var description: String,
    val creatorId: Int,
    val executorId: Int,
    val createDate: String,
    val planeExecuteDate: String,
    val factExecuteDate: String,
    val statusId: Int,
    var comment: String,
    val deleted: Boolean
)

enum class NoteStatus {
    ACTIVE,
    CANCELED,
    EXECUTED
}
