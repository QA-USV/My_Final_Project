package ru.netology.fmhandroid.repository.newsRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.NewsWithCreators

interface NewsRepository {
    suspend fun refreshNews()
    suspend fun modificationOfExistingNews(newsItem: News): News
    suspend fun createNews(newsItem: News): News
    suspend fun removeNewsItemById(id: Int)
    fun getAllNewsCategories(): Flow<List<News.Category>>
    // Метод подлежит удалению после реализации добавления новых категорий
    suspend fun saveCategories()
    //
    fun getAllNews(
        coroutineScope: CoroutineScope,
        publishEnabled: Boolean? = null,
        publishDateBefore: Long? = null,
        newsCategoryId: Int? = null,
        dateStart: Long? = null,
        dateEnd: Long? = null
    ): Flow<List<NewsWithCreators>>
}