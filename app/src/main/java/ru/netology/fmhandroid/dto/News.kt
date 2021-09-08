package ru.netology.fmhandroid.dto

import ru.netology.fmhandroid.R
import java.time.LocalDateTime

data class News (
    val id: Int? = null,
    val newsCategoryId: Category? = null,
    val title: String? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    val createDate: LocalDateTime? = null,
    val publishDate: LocalDateTime? = null,
    val publishEnabled: Boolean = false,
    val deleted: Boolean = false,
) {
    sealed class Category (val name: Int, val icon: Int) {
        class Advertisement :
            Category(R.string.news_category_advertisement, R.raw.icon_advertisement)
        class Birthday :
            Category(R.string.news_category_birthday, R.raw.icon_bithday)
        class Salary :
            Category(R.string.news_category_salary, R.raw.icon_salary)
        class Union :
            Category(R.string.news_category_union, R.raw.icon_union)
        class Holiday :
            Category(R.string.news_category_holiday, R.raw.icon_holiday)
        class Massage :
            Category(R.string.news_category_massage, R.raw.icon_massage)
        class Gratitude :
            Category(R.string.news_category_gratitude, R.raw.icon_gratitude)
        class Help :
            Category(R.string.news_category_need_help, R.raw.icon_help)
    }
}