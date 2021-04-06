package ru.netology.fmhandroid.dto

data class User(
    val id: Long,
    var login: Char,
    var password: Char,
    var first_name: Char,
    var last_name: Char,
    var middle_name: Char,
    var phone_number: Char,
    var email: Char,
    val deleted: Boolean
)
