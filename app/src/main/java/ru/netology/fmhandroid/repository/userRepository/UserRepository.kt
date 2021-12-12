package ru.netology.fmhandroid.repository.userRepository

import ru.netology.fmhandroid.dto.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserInfo(): User
}