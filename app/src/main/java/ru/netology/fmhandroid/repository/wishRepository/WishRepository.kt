package ru.netology.fmhandroid.repository.wishRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.*

interface WishRepository {
    val data: Flow<List<WishWithAllUsers>>
    val dataOpenInProgress: Flow<List<WishWithAllUsers>>
    suspend fun getAllWishes(): List<Wish>
    suspend fun saveWish(wish: Wish): Wish
    suspend fun editWish(wish: Wish): Wish
    suspend fun getWishById(wishId: Int): Wish
    suspend fun saveWishCommentById(wishId: Int, comment: String): Wish
    suspend fun setWishStatusById(wishId: Int, status: Wish.Status): Wish
}