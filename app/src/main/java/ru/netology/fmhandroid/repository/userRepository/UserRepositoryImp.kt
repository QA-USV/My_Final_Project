package ru.netology.fmhandroid.repository.userRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.netology.fmhandroid.api.UserApi
import ru.netology.fmhandroid.dao.UserDao
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.entity.*
import ru.netology.fmhandroid.error.*
import java.io.IOException

class UserRepositoryImp(private val dao: UserDao) : UserRepository {
    override val data = dao.getAllUsers()
            .map(List<UserEntity>::toDto)
            .flowOn(Dispatchers.Default)

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