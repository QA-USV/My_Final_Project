package ru.netology.fmhandroid.repository.userRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.netology.fmhandroid.api.UserApi
import ru.netology.fmhandroid.dao.UserDao
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.entity.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImp @Inject constructor(
    private val userDao: UserDao,
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getAllUsers() {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: Long): User {
        TODO("Not yet implemented")
    }
}