package ru.netology.fmhandroid.dto

data class User(
    val id: Long,
    var login: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    var phoneNumber: String,
    var email: String,
    val deleted: Boolean
)