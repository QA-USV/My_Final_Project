package ru.netology.fmhandroid.dto

import java.util.*

data class Patient(
    val id : Long,
    val room_id: Long,
    var first_name: Char,
    var last_name: Char,
    var middle_name: Char,
    val birth_date: Date,
    val deleted: Boolean
)
