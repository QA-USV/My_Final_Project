package ru.netology.fmhandroid.dto

data class Patient(
    val id: Long,
    val roomId: Long,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    val birthDate: String,
    val deleted: Boolean,
    val inHospice: Boolean
)