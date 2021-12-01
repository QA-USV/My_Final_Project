package ru.netology.fmhandroid.ui.viewdata

import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.User

/**
 * Модель представления новости в списке
 */
data class NewsViewData(
    val id: Int,
    val category: News.Category,
    val title: String,
    val description: String,
    val creator: User,
    val createDate: Long,
    val publishDate: Long,
    val publishEnabled: Boolean,
    val isOpen: Boolean
)