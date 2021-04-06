package ru.netology.fmhandroid.entity

import androidx.room.Entity
import ru.netology.fmhandroid.dto.User

@Entity
data class UserEntity(
    val id: Long,
    var login: Char,
    var password: Char,
    var first_name: Char,
    var last_name: Char,
    var middle_name: Char,
    var phone_number: Char,
    var email: Char,
    val deleted: Boolean
) {
    fun toDto() = User(
        id,
        login,
        password,
        first_name,
        last_name,
        middle_name,
        phone_number,
        email,
        deleted
    )

    companion object {
        fun fromDto(dto: User) = UserEntity(
            dto.id,
            dto.login,
            dto.password,
            dto.first_name,
            dto.last_name,
            dto.middle_name,
            dto.phone_number,
            dto.email,
            dto.deleted
        )

        fun fromApi(dto: User) = UserEntity(
            dto.id,
            dto.login,
            dto.password,
            dto.first_name,
            dto.last_name,
            dto.middle_name,
            dto.phone_number,
            dto.email,
            dto.deleted
        )
    }

    fun List<UserEntity>.toDto(): List<User> = map(UserEntity::toDto)
    fun List<User>.toEntity(): List<UserEntity> = map(UserEntity::fromDto)
    fun List<User>.toApiEntity(): List<UserEntity> = map(UserEntity::fromApi)
}
