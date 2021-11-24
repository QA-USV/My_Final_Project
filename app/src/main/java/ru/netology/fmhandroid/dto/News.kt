package ru.netology.fmhandroid.dto

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import ru.netology.fmhandroid.entity.NewsCategoryEntity
import ru.netology.fmhandroid.entity.UserEntity

@kotlinx.parcelize.Parcelize
data class News(
    val id: Int? = null,
    val newsCategoryId: Int,
    val title: String = "",
    val description: String = "",
    val creatorId: Int = 1,
    val createDate: Long,
    val publishDate: Long,
    val publishEnabled: Boolean = false,
) : Parcelable {
    @kotlinx.parcelize.Parcelize
    data class Category(
        val id: Int,
        val name: String,
        val deleted: Boolean
    ) : Parcelable {
        override fun toString(): String {
            return name
        }

        enum class Type {
            Advertisement, Salary, Union, Birthday, Holiday, Massage, Gratitude, Help, Unknown
        }
    }
}

@kotlinx.parcelize.Parcelize
data class NewsWithCategory(
    @Embedded
    val newsItem: News,
    @Relation(
        entity = NewsCategoryEntity::class,
        parentColumn = "newsCategoryId",
        entityColumn = "id"
    )
    val category: News.Category
) : Parcelable

@kotlinx.parcelize.Parcelize
data class NewsWithCreators(
    @Embedded
    val news: NewsWithCategory,
    @Relation(
        entity = UserEntity::class,
        parentColumn = "creatorId",
        entityColumn = "id"
    )
    val user: User
) : Parcelable

@kotlinx.parcelize.Parcelize
data class NewsFilterArgs(
    val category: String? = null,
    val dates: List<Long>? = null
) : Parcelable