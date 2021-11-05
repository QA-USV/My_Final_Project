package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.repository.newsRepository.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val newsItemCreatedEvent = MutableSharedFlow<Unit>()
    val loadNewsExceptionEvent = MutableSharedFlow<Unit>()
    val saveNewsItemExceptionEvent = MutableSharedFlow<Unit>()
    val editNewsItemSavedEvent = MutableSharedFlow<Unit>()
    val editNewsItemExceptionEvent = MutableSharedFlow<Unit>()
    val removeNewsItemExceptionEvent = MutableSharedFlow<Unit>()
    val loadNewsCategoriesExceptionEvent = MutableSharedFlow<Unit>()

    init {
        viewModelScope.launch {
            newsRepository.saveCategories()
            newsRepository.getAllNews()
        }
    }

    val data: Flow<List<NewsWithCreators>>
    get() = newsRepository.data

    fun getAllNews() {
        viewModelScope.launch {
            try {
                newsRepository.getAllNews()
            } catch (e: Exception) {
                e.printStackTrace()
                loadNewsExceptionEvent.emit(Unit)
            }
        }
    }

    fun save(newsItem: News) {
        viewModelScope.launch {
            try {
                newsRepository.saveNewsItem(newsItem)
                newsItemCreatedEvent.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                saveNewsItemExceptionEvent.emit(Unit)
            }
        }
    }

    fun edit(newsItem: News) {
        viewModelScope.launch {
            try {
                newsRepository.editNewsItem(newsItem)
                editNewsItemSavedEvent.emit(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                editNewsItemExceptionEvent.emit(Unit)
            }
        }
    }

    fun remove(id: Int) {
        viewModelScope.launch {
            try {
                newsRepository.removeNewsItemById(id)
            } catch (e: Exception) {
                e.printStackTrace()
                removeNewsItemExceptionEvent.emit(Unit)
            }
        }
    }

    suspend fun getAllNewsCategories() =
        newsRepository.getAllNewsCategories()
            .catch { e ->
                e.printStackTrace()
                loadNewsCategoriesExceptionEvent.emit(Unit)
            }

    suspend fun filterNewsByCategory(newsCategoryId: Int) =
        newsRepository.filterNewsByCategory(newsCategoryId)
            .catch { e ->
                e.printStackTrace()
                loadNewsExceptionEvent.emit(Unit)
            }

    suspend fun filterNewsByPublishDate(dateStart: Long, dateEnd: Long) =
        newsRepository.filterNewsByPublishDate(dateStart, dateEnd)
            .catch { e ->
                e.printStackTrace()
                loadNewsExceptionEvent.emit(Unit)
            }

    suspend fun filterNewsByCategoryAndPublishDate(
        newsCategoryId: Int,
        dateStart: Long,
        dateEnd: Long
    ) = newsRepository.filterNewsByCategoryAndPublishDate(
        newsCategoryId, dateStart, dateEnd
    ).catch { e ->
        e.printStackTrace()
        loadNewsExceptionEvent.emit(Unit)
    }
}