package ru.netology.fmhandroid.dto

data class Patient(
    val id : Int,
    val roomId: Int,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    val birthDate: String,
    val shortPatientName: String,
    val deleted: Boolean
)
