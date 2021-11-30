package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.adapter.OnNewsItemClickListener
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.repository.newsRepository.NewsRepository
import ru.netology.fmhandroid.utils.Utils
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(), OnNewsItemClickListener {

    private val sortDirection = MutableStateFlow(SortDirection.ASC)
    private val clearFilter = Filter(
        newsCategoryId = null,
        dateStart = null,
        dateEnd = null
    )

    private val filterFlow = MutableStateFlow(
        clearFilter
    )

    val loadNewsExceptionEvent = MutableSharedFlow<Unit>()
    val loadNewsCategoriesExceptionEvent = MutableSharedFlow<Unit>()

    val data: Flow<List<NewsWithCreators>> by lazy {
        filterFlow.flatMapMerge { filter ->
            newsRepository.getAllNews(
                viewModelScope,
                publishEnabled = true,
                publishDateBefore = Utils.fromLocalDateTimeToTimeStamp(LocalDateTime.now()),
                newsCategoryId = filter.newsCategoryId,
                dateStart = filter.dateStart,
                dateEnd = filter.dateEnd
            ).combine(sortDirection) { news, sortDirection ->
                when (sortDirection) {
                    SortDirection.ASC -> news
                    SortDirection.DESC -> news.reversed()
                }
            }
        }.onStart { internalOnRefresh() }
    }

    fun onRefresh() {
        viewModelScope.launch {
            internalOnRefresh()
        }
    }

    private suspend fun internalOnRefresh() {
        try {
            newsRepository.refreshNews()
        } catch (e: Exception) {
            e.printStackTrace()
            loadNewsExceptionEvent.emit(Unit)
        }
    }

    fun onSortDirectionButtonClicked() {
        sortDirection.value = sortDirection.value.reverse()
    }

    suspend fun getAllNewsCategories() =
        newsRepository.getAllNewsCategories()
            .catch { e ->
                e.printStackTrace()
                loadNewsCategoriesExceptionEvent.emit(Unit)
            }

    fun onFilterNewsClicked(
        newsCategoryId: Int?,
        dateStart: Long?,
        dateEnd: Long?
    ) {
        filterFlow.value = Filter(
            newsCategoryId = newsCategoryId,
            dateStart = dateStart,
            dateEnd = dateEnd
        )
    }

    fun initializationListNewsCategories(listNewsCategories: List<News.Category>) {
        viewModelScope.launch {
            newsRepository.saveNewsCategories(listNewsCategories)
        }
    }

    enum class SortDirection {
        ASC,
        DESC;

        fun reverse() = when (this) {
            ASC -> DESC
            DESC -> ASC
        }
    }

    private class Filter(
        val newsCategoryId: Int?,
        val dateStart: Long?,
        val dateEnd: Long?
    )

    override fun onCard(newsItem: News) {
        viewModelScope.launch {
            if (newsItem.isOpen) {
                newsRepository.changeIsOpen(newsItem.copy(isOpen = false))
            } else {
                newsRepository.changeIsOpen(newsItem.copy(isOpen = true))
            }
        }
    }
}