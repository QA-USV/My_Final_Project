package ru.netology.fmhandroid.dto

import android.annotation.SuppressLint

@SuppressLint("CustomSplashScreen")
data class SplashScreen(
    val image: Int,
    val title: String,
    val titleBackground: Int,
    val titleColor: Int,
)