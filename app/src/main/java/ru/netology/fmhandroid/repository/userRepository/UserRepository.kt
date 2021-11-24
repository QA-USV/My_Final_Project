package ru.netology.fmhandroid.repository.userRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.User

interface UserRepository {
    val data: Flow<List<User>>
    suspend fun getAllUsers()
}