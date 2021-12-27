package ru.netology.fmhandroid.repository.userRepository

import retrofit2.Response
import ru.netology.fmhandroid.api.UserApi
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.exceptions.AuthorizationException
import ru.netology.fmhandroid.exceptions.UnknownException
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

    override suspend fun getUserInfo() {
        try {
            val response: Response<User> = userApi.getUserInfo()
            if (!response.isSuccessful && response.code() == 401) {
                throw AuthorizationException
            }
            currentUser = response.body() ?: throw UnknownException
        } catch (e: AuthorizationException) {
            throw AuthorizationException
        } catch (e: Exception) {
            throw UnknownException
        }
    }

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
