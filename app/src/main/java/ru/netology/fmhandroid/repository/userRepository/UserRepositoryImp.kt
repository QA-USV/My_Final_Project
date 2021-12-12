package ru.netology.fmhandroid.repository.userRepository

import ru.netology.fmhandroid.api.UserApi
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImp @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override lateinit var currentUser: User
        private set

    override lateinit var userList: List<User>
        private set

    override suspend fun getUserInfo(): User =
        Utils.makeRequest(
            request = { userApi.getUserInfo() },
            onSuccess = { body -> body }
        )

    override suspend fun getAllUsers(): List<User> =
        Utils.makeRequest(
            request = { userApi.getAllUsers() },
            onSuccess = { body -> body }
        )
}