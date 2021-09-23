package ru.netology.fmhandroid.repository.userRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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
    override val data: Flow<List<User>>
        get() = userDao.getAllUsers()
            .map { it.toDto() }
            .flowOn(Dispatchers.Default)

    override suspend fun getAllUsers() {
        val temporaryUserList = mutableListOf(User(
            id = 1,
            login = "User-1",
            password = "abcd",
            firstName = "Дмитрий",
            lastName = "Винокуров",
            middleName = "Владимирович",
            phoneNumber = "+79109008765",
            email = "Vinokurov@mail.ru",
            deleted = false
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
                deleted = false
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
                deleted = false
            )
        )
        userDao.insert(temporaryUserList.toEntity())
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