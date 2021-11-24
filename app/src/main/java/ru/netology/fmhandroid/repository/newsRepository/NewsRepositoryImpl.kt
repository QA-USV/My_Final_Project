package ru.netology.fmhandroid.repository.newsRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.netology.fmhandroid.api.NewsApi
import ru.netology.fmhandroid.dao.NewsCategoryDao
import ru.netology.fmhandroid.dao.NewsDao
import ru.netology.fmhandroid.dao.UserDao
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.entity.toNewsCategoryDto
import ru.netology.fmhandroid.entity.toNewsCategoryEntity
import ru.netology.fmhandroid.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val newsCategoryDao: NewsCategoryDao,
    private val newsApi: NewsApi
) : NewsRepository {
    //* Тестовые переменные. Подлежат удалению в будущем *

    val advertisement = News.Category(
        1,
        "Объявление",
        false
    )

    val birthday = News.Category(
        2,
        "День рождения",
        false
    )

    val salary = News.Category(
        3,
        "Зарплата",
        false
    )

    val union = News.Category(
        4,
        "Профсоюз",
        false
    )

    val holiday = News.Category(
        5,
        "Праздник",
        false
    )

    val massage = News.Category(
        6,
        "Массаж",
        false
    )

    val gratitude = News.Category(
        7,
        "Благодарность",
        false
    )

    val help = News.Category(
        8,
        "Нужна помощь",
        false
    )

    private val categories =
        listOf(advertisement, salary, union, birthday, holiday, massage, gratitude, help)

    //-------------------------------------------------------------//

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

    override suspend fun refreshNews() = Utils.makeRequest(
        request = { newsApi.getAllNews() },
        onSuccess = { body ->
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

    // Метод подлежит удалению после реализации добавления новых категорий
    override suspend fun saveCategories() =
        newsCategoryDao.insert(categories.toNewsCategoryEntity())
}