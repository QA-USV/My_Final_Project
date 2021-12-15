package ru.netology.fmhandroid.repository.userRepository

import ru.netology.fmhandroid.api.UserApi
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override var currentUser: User = Utils.Empty.emptyUser
        private set

    override var userList: List<User> = emptyList()
        private set

    override suspend fun getUserInfo(): User =
        Utils.makeRequest(
            request = { userApi.getUserInfo() },
            onSuccess = { body ->
                body.also {
                    currentUser = it
                }
            }
        )

    override suspend fun getAllUsers(): List<User> =
        Utils.makeRequest(
            request = { userApi.getAllUsers() },
            onSuccess = { body ->
                body.also {
                    userList = it
                }
            }
        )

    override fun userLogOut() {
        currentUser = Utils.Empty.emptyUser
    }
}