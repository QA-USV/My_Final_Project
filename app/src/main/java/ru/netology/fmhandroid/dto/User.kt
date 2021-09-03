package ru.netology.fmhandroid.dto

data class User(
    val id: Int,
    var login: String? = null,
    var password: String? = null,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    var phoneNumber: String? = null,
    var email: String? = null,
    val deleted: Boolean
)
