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
    @ColumnInfo(name = "admin")
    val admin: Boolean,
    @ColumnInfo(name = "firstName")
    var firstName: String,
    @ColumnInfo(name = "lastName")
    var lastName: String,
    @ColumnInfo(name = "middleName")
    var middleName: String,
) {
    fun toDto() = User(
        id = id,
        admin = admin,
        firstName = firstName,
        lastName = lastName,
        middleName = middleName,
    )
}

fun List<UserEntity>.toDto(): List<User> = map(UserEntity::toDto)
fun List<User>.toEntity(): List<UserEntity> = map(User::toEntity)
fun User.toEntity() = UserEntity(
    id = id,
    admin = admin,
    firstName = firstName,
    lastName = lastName,
    middleName = middleName,
)
