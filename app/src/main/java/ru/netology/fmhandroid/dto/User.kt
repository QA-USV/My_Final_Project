package ru.netology.fmhandroid.dto

data class User(
    val id: Int? = null,
    val login: String? = null,
    val password: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val deleted: Boolean = false,
)