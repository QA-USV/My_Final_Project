package ru.netology.fmhandroid.repository.wishRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.WishApi
import ru.netology.fmhandroid.dao.WishDao
import ru.netology.fmhandroid.dto.Wish
import ru.netology.fmhandroid.dto.Wish.Status
import ru.netology.fmhandroid.entity.toDto
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.utils.Utils.makeRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishRepositoryImp @Inject constructor(
    private val wishDao: WishDao,
    private val wishApi: WishApi
) : WishRepository {

    override val data: Flow<List<Wish>>
        get() = wishDao.getAllWishes()
            .map { it.toDto() }
            .flowOn(Dispatchers.Default)

    override suspend fun getAllWishes(): Flow<List<Wish>> = flow {
        makeRequest(
            request = { wishApi.getAllWishes() },
            onSuccess = { body ->
                wishDao.insert(body.toEntity())
                emit(body)
            }
        )
    }

    override suspend fun saveWish(wish: Wish): Wish = makeRequest(
        request = { wishApi.saveWish(wish) },
        onSuccess = { body ->
            wishDao.insert(body.toEntity())
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

    override suspend fun setWishStatusById(wishId: Int, status: Status): Wish = makeRequest(
        request = { wishApi.setWishStatusById(wishId, status) },
        onSuccess = { body ->
            wishDao.insert(body.toEntity())
            body
        }
    )
}