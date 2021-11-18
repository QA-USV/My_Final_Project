package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.repository.newsRepository.NewsRepository
import javax.inject.Inject


@HiltViewModel
class NewsControlPanelViewModel @Inject constructor (
    private val newsRepository: NewsRepository
        ): ViewModel() {

    private val sortDirection = MutableStateFlow(NewsViewModel.SortDirection.ASC)

    val loadNewsExceptionEvent = MutableSharedFlow<Unit>()
    val newsItemCreatedEvent = MutableSharedFlow<Unit>()
    val saveNewsItemExceptionEvent = MutableSharedFlow<Unit>()
    val editNewsItemSavedEvent = MutableSharedFlow<Unit>()
    val editNewsItemExceptionEvent = MutableSharedFlow<Unit>()
    val removeNewsItemExceptionEvent = MutableSharedFlow<Unit>()

    val data: Flow<List<NewsWithCreators>> by lazy {
        newsRepository.getAllNews(
            viewModelScope
        ).combine(sortDirection) { news, sortDirection ->
            when(sortDirection) {
                NewsViewModel.SortDirection.ASC -> news
                NewsViewModel.SortDirection.DESC -> news.reversed()
            }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            try {
                newsRepository.refreshNews()
            } catch (e: Exception) {
                e.printStackTrace()
                loadNewsExceptionEvent.emit(Unit)
            }
        }
    }

    fun onSortDirectionButtonClicked() {
        sortDirection.value = sortDirection.value.reverse()
    }

    fun onFilterNewsClicked(
        newsCategoryId: Int?,
        dateStart: Long?,
        dateEnd: Long?
    ) {
        newsRepository.getAllNews(
            viewModelScope,
            publishEnabled = null,
            publishDateBefore = null,
            newsCategoryId,
            dateStart,
            dateEnd
        )
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

    fun getAllNewsCategories() = newsRepository.getAllNewsCategories()


    enum class SortDirection {
        ASC,
        DESC;
        fun reverse() = when(this) {
            ASC -> DESC
            DESC -> ASC
        }
    }
}
