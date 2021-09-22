package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.repository.newsRepository.NewsRepository
import ru.netology.fmhandroid.utils.Events
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val newsItemCreatedEvent = Events()
    val loadNewsExceptionEvent = Events()
    val saveNewsItemExceptionEvent = Events()
    val removeNewsItemExceptionEvent = Events()
    val loadNewsCategoriesExceptionEvent = Events()

    init {
        viewModelScope.launch {
            newsRepository.saveCategories()
            newsRepository.getAllNews()
                .catch { e ->
                    e.printStackTrace()
                    Events.produceEvents(loadNewsExceptionEvent)
                }.collect()

        }
    }

    val data = newsRepository.data

    fun save(newsItem: News) {
        viewModelScope.launch {
            try {
                newsRepository.saveNewsItem(newsItem)
                Events.produceEvents(newsItemCreatedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(saveNewsItemExceptionEvent)
            }
        }
    }

    fun edit(newsItem: News) {
        viewModelScope.launch {
            try {
                newsRepository.editNewsItem(newsItem)
                Events.produceEvents(newsItemCreatedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(saveNewsItemExceptionEvent)
            }
        }
    }

    fun remove(id: Int) {
        viewModelScope.launch {
            try {
                newsRepository.removeNewsItemById(id)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(removeNewsItemExceptionEvent)
            }
        }
    }

    suspend fun getAllNewsCategories() =
        newsRepository.getAllNewsCategories()
            .catch { e ->
                e.printStackTrace()
                Events.produceEvents(loadNewsCategoriesExceptionEvent)
            }

    suspend fun filterNewsByCategory(newsCategoryId: Int) =
        newsRepository.filterNewsByCategory(newsCategoryId)
            .catch { e ->
                e.printStackTrace()
                Events.produceEvents(loadNewsExceptionEvent)
            }

    suspend fun filterNewsByPublishDate(dateStart: Long, dateEnd: Long) =
        newsRepository.filterNewsByPublishDate(dateStart, dateEnd)
            .catch { e ->
                e.printStackTrace()
                Events.produceEvents(loadNewsExceptionEvent)
            }

    suspend fun filterNewsByCategoryAndPublishDate(
        newsCategoryId: Int,
        dateStart: Long,
        dateEnd: Long
    ) = newsRepository.filterNewsByCategoryAndPublishDate(
        newsCategoryId, dateStart, dateEnd
    ).catch { e ->
        e.printStackTrace()
        Events.produceEvents(loadNewsExceptionEvent)
    }
}