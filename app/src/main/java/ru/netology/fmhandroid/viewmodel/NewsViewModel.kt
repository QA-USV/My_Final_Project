package ru.netology.fmhandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.exceptions.ServerException
import ru.netology.fmhandroid.repository.newsRepository.NewsRepository
import ru.netology.fmhandroid.utils.Utils
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

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

    init {
        viewModelScope.launch {
            newsRepository.saveCategories()
        }
    }

    val data: Flow<List<NewsWithCreators>> by lazy {
        filterFlow.flatMapMerge { filter ->
            newsRepository.getAllNews(
                viewModelScope,
                publishEnabled = true,
                // Вынести в Utils
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
        }.catch {
            loadNewsExceptionEvent.emit(Unit)
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            try {
                newsRepository.refreshNews()
                filterFlow.value = clearFilter
            } catch (e: Exception) {
                e.printStackTrace()
                loadNewsExceptionEvent.emit(Unit)
            }
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

//    suspend fun filterNewsByCategory(newsCategoryId: Int) =
//        newsRepository.filterNewsByCategory(newsCategoryId)
//            .catch { e ->
//                e.printStackTrace()
//                loadNewsExceptionEvent.emit(Unit)
//            }
//
//    suspend fun filterNewsByPublishDate(dateStart: Long, dateEnd: Long) =
//        newsRepository.filterNewsByPublishDate(dateStart, dateEnd)
//            .catch { e ->
//                e.printStackTrace()
//                loadNewsExceptionEvent.emit(Unit)
//            }
//
//    suspend fun filterNewsByCategoryAndPublishDate(
//        newsCategoryId: Int,
//        dateStart: Long,
//        dateEnd: Long
//    ) = newsRepository.filterNewsByCategoryAndPublishDate(
//        newsCategoryId, dateStart, dateEnd
//    ).catch { e ->
//        e.printStackTrace()
//        loadNewsExceptionEvent.emit(Unit)
//    }

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
}