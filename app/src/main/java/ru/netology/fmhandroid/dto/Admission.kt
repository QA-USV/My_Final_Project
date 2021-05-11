package ru.netology.fmhandroid.dto

data class Admission(
    val id: Int,
    val dateIn: String,
    val dateOut: String,
    val factIn: Boolean = false,
    val factOut: Boolean = false,
    val deleted: Boolean = false,
    val patientId: Int
)
