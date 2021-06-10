package ru.netology.fmhandroid.model

import ru.netology.fmhandroid.dto.Note

data class NoteModel(
    val notes: List<Note> = emptyList(),
    val empty: Boolean = false,
    )
