package ru.netology.fmhandroid.repository.newsRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.NewsWithCreators

interface NewsRepository {
    suspend fun refreshNews()
    suspend fun editNewsItem(newsItem: News): News
    suspend fun saveNewsItem(newsItem: News): News
    suspend fun removeNewsItemById(id: Int)
//    suspend fun filterNewsByCategory(newsCategoryId: Int): Flow<List<NewsWithCreators>>
//    suspend fun filterNewsByPublishDate(
//        dateStart: Long,
//        dateEnd: Long
//    ): Flow<List<NewsWithCreators>>
//
//    suspend fun filterNewsByCategoryAndPublishDate(
//        newsCategoryId: Int,
//        dateStart: Long,
//        dateEnd: Long
//    ): Flow<List<NewsWithCreators>>

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