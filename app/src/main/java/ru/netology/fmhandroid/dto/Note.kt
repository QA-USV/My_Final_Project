package ru.netology.fmhandroid.dto

data class Note(
    val id: Int = 0,
    val patientId: Int = 0,
    var description: String = "",
    val creatorId: Int = 0,
    val executorId: Int = 0,
    val createDate: String = "",
    val planeExecuteDate: String = "",
    val factExecuteDate: String = "",
    val statusId: Int = 0,
    val status: Status = Status.ACTIVE,
    var comment: String = "",
    val deleted: Boolean = false
) {

    enum class Status {
        ACTIVE,
        CANCELED,
        EXECUTE
    }
}