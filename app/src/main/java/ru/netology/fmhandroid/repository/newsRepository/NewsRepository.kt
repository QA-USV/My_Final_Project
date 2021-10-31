package ru.netology.fmhandroid.repository.newsRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.NewsWithCreators

interface NewsRepository {
    val data: Flow<List<NewsWithCreators>>
    suspend fun getAllNews(): List<News>
    suspend fun editNewsItem(newsItem: News): News
    suspend fun saveNewsItem(newsItem: News): News
    suspend fun removeNewsItemById(id: Int)
    suspend fun filterNewsByCategory(newsCategoryId: Int): Flow<List<NewsWithCreators>>
    suspend fun filterNewsByPublishDate(dateStart: Long, dateEnd: Long): Flow<List<NewsWithCreators>>
    suspend fun filterNewsByCategoryAndPublishDate(
        newsCategoryId: Int,
        dateStart: Long,
        dateEnd: Long
    ): Flow<List<NewsWithCreators>>
    suspend fun getAllNewsCategories(): Flow<List<News.Category>>
    // Метод подлежит удалению после реализации добавления новых категорий
    suspend fun saveCategories()
}