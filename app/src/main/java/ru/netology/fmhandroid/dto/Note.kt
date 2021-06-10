package ru.netology.fmhandroid.dto

import ru.netology.fmhandroid.enum.ExecutionPriority

data class Note(
    val id: Int,
    val patientId: Int? = null,
    var description: String,
    val creatorId: Int? = null,
    val executorId: Int? = null,
    val createDate: String? = null,
    val planeExecuteDate: String,
    val factExecuteDate: String,
    val executionPriority: ExecutionPriority,
    val noteStatus: NoteStatus? = null,
    var comment: String? = null,
    val deleted: Boolean = false,
    val shortExecutorName: String,
    val shortPatientName: String
)

enum class NoteStatus {
    ACTIVE,
    CANCELED,
    EXECUTED
}
