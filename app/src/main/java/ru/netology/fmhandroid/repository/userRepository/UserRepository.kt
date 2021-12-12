package ru.netology.fmhandroid.repository.userRepository

import ru.netology.fmhandroid.dto.User

interface UserRepository {
    val currentUser: User
    val userList: List<User>
    suspend fun getAllUsers(): List<User>
    suspend fun getUserInfo(): User
}