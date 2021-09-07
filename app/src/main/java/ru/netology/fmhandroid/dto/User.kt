package ru.netology.fmhandroid.dto

data class User(
    val id: Int? = null,
    var login: String? = null,
    var password: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var middleName: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    val deleted: Boolean = false
)
