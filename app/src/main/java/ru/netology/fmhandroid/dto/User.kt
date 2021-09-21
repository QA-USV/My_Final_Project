package ru.netology.fmhandroid.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@kotlinx.parcelize.Parcelize
data class User(
    var id: Int? = null,
    val login: String? = null,
    val password: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val deleted: Boolean = false,
) : Parcelable