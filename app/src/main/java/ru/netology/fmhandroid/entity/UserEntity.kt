package ru.netology.fmhandroid.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.User

@Entity
data class UserEntity(
    @PrimaryKey
    val id: Int,
    var login: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    var phoneNumber: String,
    var email: String,
    val deleted: Boolean
) {
    fun toDto() = User(
        id,
        login,
        password,
        firstName,
        lastName,
        middleName,
        phoneNumber,
        email,
        deleted
    )

    companion object {
        fun fromDto(dto: User) = UserEntity(
            dto.id,
            dto.login,
            dto.password,
            dto.firstName,
            dto.lastName,
            dto.middleName,
            dto.phoneNumber,
            dto.email,
            dto.deleted
        )
    }

    fun List<UserEntity>.toDto(): List<User> = map(UserEntity::toDto)
    fun List<User>.toEntity(): List<UserEntity> = map(UserEntity::fromDto)
}
