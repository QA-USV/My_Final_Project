package ru.netology.fmhandroid.repository.userRepository

import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserInfo(): User
}