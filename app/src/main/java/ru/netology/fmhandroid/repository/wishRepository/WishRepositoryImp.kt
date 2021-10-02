package ru.netology.fmhandroid.repository.wishRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.WishApi
import ru.netology.fmhandroid.dao.WishDao
import ru.netology.fmhandroid.domain.BusinessRules
import ru.netology.fmhandroid.dto.*
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.utils.Utils.makeRequest
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishRepositoryImp @Inject constructor(
    private val wishDao: WishDao,
    private val wishApi: WishApi
) : WishRepository {

    override val dataOpenInProgress: Flow<List<WishWithAllUsers>>
        get() = wishDao.getWishesOpenAndInProgressStatuses(
            Wish.Status.OPEN,
            Wish.Status.IN_PROGRESS
        ).flowOn(Dispatchers.Default)

    override val data: Flow<List<WishWithAllUsers>>
        get() = wishDao.getAllWishes()
            .flowOn(Dispatchers.Default)

    override lateinit var dataComments: Flow<List<WishCommentWithCreator>>

    override suspend fun getAllWishes(): List<Wish> = makeRequest(
        request = { wishApi.getAllWishes() },
        onSuccess = { body ->
            body.map {
                it.priority = BusinessRules.determiningPriorityLevelOfWish(
                    LocalDateTime.now(),
                    Utils.fromLongToLocalDateTime(it.planExecuteDate!!)
                )
            }
            wishDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun getAllCommentsForWish(id: Int): List<WishComment> = makeRequest(
        request = { wishApi.getAllWishComments(id) },
        onSuccess = { body ->
            wishDao.insertComment(body.toEntity())
            dataComments = wishDao.getWishComments(id).flowOn(Dispatchers.Default)
            body
        }
    )

    override suspend fun saveWish(wish: Wish): Wish = makeRequest(
        request = { wishApi.saveWish(wish) },
        onSuccess = { body ->
            wishDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun saveWishComment(wishId: Int, comment: WishComment): WishComment =
        makeRequest(
            request = { wishApi.saveWishComment(wishId, comment) },
            onSuccess = { body ->
                wishDao.insertComment(body.toEntity())
                dataComments.map { list ->
                    list.map {
                        if (it.wishComment.id != comment.id) {
                            it
                        } else {
                            it.wishComment.copy(description = comment.description)
                        }
                    }
                }
                body
            }
        )

    override suspend fun editWish(wish: Wish) = makeRequest(
        request = { wishApi.editWish(wish) },
        onSuccess = { body ->
            wishDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun changeWishComment(comment: WishComment): WishComment = makeRequest(
        request = { wishApi.updateWishComment(comment) },
        onSuccess = { body ->
            wishDao.insertComment(body.toEntity())
            dataComments.map { list ->
                list.map {
                    if (it.wishComment.id == comment.id) {
                        it.wishComment.copy(description = comment.description)
                    } else {
                        it
                    }
                }
            }
            body
        }
    )

    override suspend fun getWishById(wishId: Int): Wish = makeRequest(
        request = { wishApi.getWishById(wishId) },
        onSuccess = { body ->
            wishDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun saveWishCommentById(wishId: Int, comment: String): Wish = makeRequest(
        request = { wishApi.saveWishCommentById(wishId, comment) },
        onSuccess = { body ->
            wishDao.insert(body.toEntity())
            body
        }
    )

    override suspend fun setWishStatusById(wishId: Int, status: Wish.Status): Wish = makeRequest(
        request = { wishApi.setWishStatusById(wishId, status) },
        onSuccess = { body ->
            wishDao.insert(body.toEntity())
            body
        }
    )
}