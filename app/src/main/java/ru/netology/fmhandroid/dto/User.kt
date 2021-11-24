package ru.netology.fmhandroid.dto

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class User(
    var id: Int,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val phoneNumber: String,
    val email: String,
) : Parcelable