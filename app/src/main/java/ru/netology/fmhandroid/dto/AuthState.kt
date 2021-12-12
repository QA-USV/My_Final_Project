package ru.netology.fmhandroid.dto

data class AuthState(
        val accessToken: String,
        val refreshToken: String
    )