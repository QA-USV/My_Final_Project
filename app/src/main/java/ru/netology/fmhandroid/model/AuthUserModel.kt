package ru.netology.fmhandroid.model

import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.utils.Utils

data class AuthUserModel (
    val authUser: User = Utils.Empty.emptyUser
        )