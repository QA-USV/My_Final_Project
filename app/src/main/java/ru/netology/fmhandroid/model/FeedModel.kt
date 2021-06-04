package ru.netology.fmhandroid.model

data class FeedModel(
    val dto: List<Any> = emptyList(),
    val empty: Boolean = false,
)