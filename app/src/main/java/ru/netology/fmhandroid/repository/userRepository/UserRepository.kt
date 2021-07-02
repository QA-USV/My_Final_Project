package ru.netology.fmhandroid.repository.userRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.*

interface UserRepository {
    suspend fun getAllUsers()
    suspend fun saveUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun getUserById(id: Long): User
}