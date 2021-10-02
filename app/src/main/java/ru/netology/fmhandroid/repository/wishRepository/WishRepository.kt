package ru.netology.fmhandroid.repository.wishRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.*

interface WishRepository {
    val data: Flow<List<WishWithAllUsers>>
    val dataComments: Flow<List<WishCommentWithCreator>>
    val dataOpenInProgress: Flow<List<WishWithAllUsers>>
    suspend fun getAllWishes(): List<Wish>
    suspend fun saveWish(wish: Wish): Wish
    suspend fun editWish(wish: Wish): Wish
    suspend fun getAllCommentsForWish(id: Int): List<WishComment>
    suspend fun saveWishComment(wishId: Int, comment: WishComment): WishComment
    suspend fun changeWishComment(comment: WishComment): WishComment
    suspend fun getWishById(wishId: Int): Wish
    suspend fun saveWishCommentById(wishId: Int, comment: String): Wish
    suspend fun setWishStatusById(wishId: Int, status: Wish.Status): Wish
}