package ru.netology.fmhandroid.dto

data class Admission(
        val id: Long,
        val dateIn: String,
        val dateOut: String,
        val factIn: String,
        val factOut: String,
        val deleted: Boolean,
        val patientId: Long
)