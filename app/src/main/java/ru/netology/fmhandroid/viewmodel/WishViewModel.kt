package ru.netology.fmhandroid.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.Wish
import ru.netology.fmhandroid.repository.wishRepository.WishRepository
import ru.netology.fmhandroid.utils.SingleLiveEvent
import javax.inject.Inject

private var emptyWish = Wish()

@HiltViewModel
class WishViewModel @Inject constructor(
    private val wishRepository: WishRepository
) : ViewModel() {

    val data: Flow<List<Wish>>
        get() = wishRepository.data

    private val _wishCreatedEvent = SingleLiveEvent<Unit>()
    val wishCreatedEvent: LiveData<Unit>
        get() = _wishCreatedEvent

    private val _loadWishExceptionEvent = SingleLiveEvent<Unit>()
    val loadWishExceptionEvent: LiveData<Unit>
        get() = _loadWishExceptionEvent

    private val _saveWishExceptionEvent = SingleLiveEvent<Unit>()
    val saveWishExceptionEvent: LiveData<Unit>
        get() = _saveWishExceptionEvent

    init {
        viewModelScope.launch {
            wishRepository.getAllWishes().collect()
        }
    }

    suspend fun getAllWishes() {
        viewModelScope.launch {
            try {
                wishRepository.getAllWishes()
            } catch (e: Exception) {
                e.printStackTrace()
                _loadWishExceptionEvent.call()
            }
        }
    }

    fun save() {
        emptyWish.let {
            viewModelScope.launch {
                try {
                    wishRepository.saveWish(it)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _saveWishExceptionEvent.call()
                }
                _wishCreatedEvent.call()
            }
            emptyWish = Wish()
        }
    }

    fun editWish(wish: Wish) {
        emptyWish = wish
    }

    fun getWishById(id: Int) {
        viewModelScope.launch {
            try {
                wishRepository.getWishById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveWishCommentById() {
        emptyWish.let {
            viewModelScope.launch {
                try {
                    it.comment?.let { comment -> wishRepository.saveWishCommentById(it.id, comment) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun setWishStatusById() {
        emptyWish.let {
            viewModelScope.launch {
                try {
                    it.wishStatus?.let { noteStatus -> wishRepository.setWishStatusById(it.id, noteStatus) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}