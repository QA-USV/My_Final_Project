package ru.netology.fmhandroid.repository.userRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.User

interface UserRepository {
    val data: Flow<List<User>>
    suspend fun getAllUsers()
    suspend fun saveUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun getUserById(id: Long): User
}