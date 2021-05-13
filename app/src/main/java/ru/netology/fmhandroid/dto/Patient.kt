package ru.netology.fmhandroid.dto

data class Patient(
    val id : Int,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    val birthDate: String,
    val currentAdmissionId: Int,
    val deleted: Boolean
)
