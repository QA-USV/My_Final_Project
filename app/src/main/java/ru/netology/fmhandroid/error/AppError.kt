package ru.netology.fmhandroid.error

import android.database.SQLException
import java.io.IOException

sealed class AppError(var code: String) : RuntimeException() {
    companion object {
        fun from(e: Throwable): AppError = when (e) {
            is AppError -> e
            is SQLException -> DbError
            is IOException -> ServerError
            else -> UnknownError
        }
    }
}

class ApiError(val status: Int, code: String) : AppError(code)
object ServerError : AppError("error_server")
object DbError : AppError("error_db")
object UnknownError : AppError("error_unknown")