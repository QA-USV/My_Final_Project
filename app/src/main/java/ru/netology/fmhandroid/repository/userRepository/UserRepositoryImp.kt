package ru.netology.fmhandroid.repository.userRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.fmhandroid.api.UserApi
import ru.netology.fmhandroid.dao.UserDao
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.entity.toDto
import ru.netology.fmhandroid.entity.toEntity
import ru.netology.fmhandroid.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImp @Inject constructor(
    private val userDao: UserDao,
    private val userApi: UserApi,
) : UserRepository {
    override val data: Flow<List<User>>
        get() = userDao.getAllUsers()
            .map { it.toDto() }
            .flowOn(Dispatchers.Default)

    override suspend fun getAllUsers() {
        val temporaryUserList = listOf(
            User(
                id = 1,
                login = "User-1",
                password = "abcd",
                firstName = "Дмитрий",
                lastName = "Винокуров",
                middleName = "Владимирович",
                phoneNumber = "+79109008765",
                email = "Vinokurov@mail.ru",
            ),
            User(
                id = 2,
                login = "User-2",
                password = "abcd",
                firstName = "Роман",
                lastName = "Виктюк",
                middleName = "Григорьевич",
                phoneNumber = "+79108009876",
                email = "Viktyuk@mail.ru",
            ),
            User(
                id = 3,
                login = "User-3",
                password = "abcd",
                firstName = "Валентина",
                lastName = "Скоморохова-Преображенская",
                middleName = "Вениаминовна",
                phoneNumber = "+79001237645",
                email = "VVS-P@yandex.ru",
            )
        )
        userDao.insert(temporaryUserList.toEntity())
    }

    override suspend fun getUserInfo(): User =
        Utils.makeRequest(
            request = { userApi.getUserInfo() },
            onSuccess = { body -> body }
        )
}