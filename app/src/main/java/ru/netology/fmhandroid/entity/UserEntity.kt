package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.User

@Entity(tableName = "UserEntity")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "login")
    var login: String?,
    @ColumnInfo(name = "password")
    var password: String?,
    @ColumnInfo(name = "firstName")
    var firstName: String,
    @ColumnInfo(name = "lastName")
    var lastName: String,
    @ColumnInfo(name = "middleName")
    var middleName: String,
    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String?,
    @ColumnInfo(name = "email")
    var email: String?,
    @ColumnInfo(name = "deleted")
    val deleted: Boolean
) {
    fun toDto() = User(
        id = id,
        login = login,
        password = password,
        firstName = firstName,
        lastName = lastName,
        middleName = middleName,
        phoneNumber = phoneNumber,
        email = email,
        deleted = deleted
    )
}

fun List<UserEntity>.toDto(): List<User> = map(UserEntity::toDto)
fun List<User>.toEntity(): List<UserEntity> = map(User::toEntity)
fun User.toEntity() = UserEntity(
    id = id,
    login = login,
    password = password,
    firstName = firstName,
    lastName = lastName,
    middleName = middleName,
    phoneNumber = phoneNumber,
    email = email,
    deleted = deleted
)
