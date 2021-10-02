package ru.netology.fmhandroid.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.dto.*
import ru.netology.fmhandroid.repository.wishRepository.WishRepository
import ru.netology.fmhandroid.utils.Events
import javax.inject.Inject


@HiltViewModel
class WishViewModel @Inject constructor(
    private val wishRepository: WishRepository
) : ViewModel() {
    lateinit var commentsData: Flow<List<WishCommentWithCreator>>

    val data: Flow<List<WishWithAllUsers>>
        get() = wishRepository.data

    val dataOpenInProgress: Flow<List<WishWithAllUsers>>
        get() = wishRepository.dataOpenInProgress

    val wishCreatedEvent = Events()
    val wishCommentCreatedEvent = Events()
    val wishCommentsLoadedEvent = Events()
    val wishCommentUpdatedEvent = Events()
    val wishStatusChangedEvent = Events()
    val wishUpdatedEvent = Events()
    val wishCommentsLoadExceptionEvent = Events()
    val wishCommentCreateExceptionEvent = Events()
    val updateWishCommentExceptionEvent = Events()
    val wishStatusChangeExceptionEvent = Events()
    val loadWishExceptionEvent = Events()
    val createWishExceptionEvent = Events()
    val wishUpdateExceptionEvent = Events()
    val wishLoadedEvent = Events()

    init {
        viewModelScope.launch {
            wishRepository.getAllWishes()
        }
    }

    suspend fun getAllWishes() {
        viewModelScope.launch {
            try {
                wishRepository.getAllWishes()
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(loadWishExceptionEvent)
            }
        }
    }

    fun getAllWishComments(id: Int) {
        viewModelScope.launch {
            try {
                wishRepository.getAllCommentsForWish(id)
                commentsData = wishRepository.dataComments
                Events.produceEvents(wishCommentsLoadedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(wishCommentsLoadExceptionEvent)
            }
        }
    }

    fun save(wish: Wish) {
        viewModelScope.launch {
            try {
                wishRepository.saveWish(wish)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(createWishExceptionEvent)
            }
        }
    }

    fun createClaimComment(wishComment: WishComment) {
        viewModelScope.launch {
            try {
                wishComment.wishId?.let { wishRepository.saveWishComment(it, wishComment) }
                Events.produceEvents(wishCommentCreatedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(wishCommentCreateExceptionEvent)
            }
        }
    }

    fun editWish(wish: Wish) {
        TODO("Дописать")
    }

    fun updateWishComment(comment: WishComment) {
        viewModelScope.launch {
            try {
                wishRepository.changeWishComment(comment)
                Events.produceEvents(wishCommentUpdatedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
                Events.produceEvents(updateWishCommentExceptionEvent)
            }
        }
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

    fun setWishStatusById() {
        TODO("Дописать")
    }
}