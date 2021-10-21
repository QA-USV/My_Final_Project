package ru.netology.fmhandroid.model

data class FeedModelState(
    val loading: Boolean = false,
    val errorLoading: Boolean = false,
    val errorSaving: Boolean = false
)
