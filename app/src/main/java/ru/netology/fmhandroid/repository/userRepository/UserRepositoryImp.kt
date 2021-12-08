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
                admin = false,
                firstName = "Дмитрий",
                lastName = "Винокуров",
                middleName = "Владимирович",
            ),
            User(
                id = 2,
                admin = false,
                firstName = "Роман",
                lastName = "Виктюк",
                middleName = "Григорьевич",
            ),
            User(
                id = 3,
                admin = false,
                firstName = "Валентина",
                lastName = "Скоморохова-Преображенская",
                middleName = "Вениаминовна",
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