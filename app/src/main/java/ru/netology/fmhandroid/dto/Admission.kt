package ru.netology.fmhandroid.dto

data class Admission(
        val id: Long,
        val patientId: Long,
        val comment: String,
        val dateIn: String,
        val dateOut: String,
        val factIn: String,
        val factOut: String,
        val deleted: Boolean
)