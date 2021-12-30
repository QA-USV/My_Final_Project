package ru.netology.fmhandroid.repository.newsRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.NewsApi
import ru.netology.fmhandroid.dao.NewsCategoryDao
import ru.netology.fmhandroid.dao.NewsDao
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.entity.*
import ru.netology.fmhandroid.exceptions.ServerException
import ru.netology.fmhandroid.utils.Utils
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val newsCategoryDao: NewsCategoryDao,
    private val newsApi: NewsApi
) : NewsRepository {
    override fun getAllNews(
        coroutineScope: CoroutineScope,
        publishEnabled: Boolean?,
        publishDateBefore: Long?,
        newsCategoryId: Int?,
        dateStart: Long?,
        dateEnd: Long?
    ) = newsDao.getAllNews(
        publishEnabled = publishEnabled,
        publishDateBefore = publishDateBefore,
        newsCategoryId = newsCategoryId,
        dateStart = dateStart,
        dateEnd = dateEnd
    ).flowOn(Dispatchers.Default)

    override suspend fun changeIsOpen(newsItem: News) {
        newsDao.insert(newsItem.toEntity())
    }

    override suspend fun refreshNews() = Utils.makeRequest(
        request = { newsApi.getAllNews() },
        onSuccess = { body ->
            val apiId = body
                .map { it.id }
            val databaseId = newsDao.getAllNewsList()
                .map{ it.newsItem.id}
                .toMutableList()
            databaseId.removeAll(apiId)
            newsDao.removeNewsItemsByIdList(databaseId)
            newsDao.insert(body.toEntity())
        }
    )

    override suspend fun modificationOfExistingNews(newsItem: News): News =
        Utils.makeRequest(
            request = { newsApi.editNewsItem(newsItem) },
            onSuccess = { body ->
                newsDao.insert(body.toEntity())
                body
            }
        )

    override suspend fun createNews(newsItem: News): News =
        Utils.makeRequest(
            request = { newsApi.saveNewsItem(newsItem) },
            onSuccess = { body ->
                newsDao.insert(body.toEntity())
                body
            }
        )

    override suspend fun removeNewsItemById(id: Int) =
        Utils.makeRequest(
            request = { newsApi.removeNewsItemById(id) },
            onSuccess = {
                newsDao.removeNewsItemById(id)
            }
        )

    override fun getAllNewsCategories(): Flow<List<News.Category>> =
        newsCategoryDao.getAllNewsCategories().map { it.toNewsCategoryDto() }

    override suspend fun saveNewsCategories(listNewsCategories: List<News.Category>) =
        newsCategoryDao.insert(listNewsCategories.toNewsCategoryEntity())
}