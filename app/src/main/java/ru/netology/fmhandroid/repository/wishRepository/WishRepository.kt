package ru.netology.fmhandroid.repository.wishRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.*

interface WishRepository {
    val data: Flow<List<WishWithAllUsers>>
    suspend fun getAllWishes(): Flow<List<Wish>>
    suspend fun saveWish(wish: Wish): Wish
    suspend fun editWish(wish: Wish): Wish
    suspend fun getWishById(id: Int): Wish
    suspend fun saveWishCommentById(noteId: Int, comment: String): Wish
    suspend fun setWishStatusById(noteId: Int, status: Wish.Status): Wish
}