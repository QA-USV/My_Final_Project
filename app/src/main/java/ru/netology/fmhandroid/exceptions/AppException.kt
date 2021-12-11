package ru.netology.fmhandroid.exceptions

import android.database.SQLException
import java.io.IOException
import java.lang.IllegalArgumentException

sealed class AppException(var code: String) : RuntimeException() {
    companion object {
        fun from(e: Throwable): AppException = when (e) {
            is AppException -> e
            is IllegalArgumentException -> AuthorizationException
            is SQLException -> DbException
            is IOException -> ServerException
            else -> UnknownException
        }
    }
}

class ApiException(val status: Int, code: String) : AppException(code)
object AuthorizationException : AppException("authorization_failed")
object ServerException : AppException("error_server")
object DbException : AppException("error_db")
object UnknownException : AppException("error_unknown")