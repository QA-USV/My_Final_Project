package ru.netology.fmhandroid.utils

import retrofit2.Response
import ru.netology.fmhandroid.exceptions.ApiException
import ru.netology.fmhandroid.exceptions.ServerException
import ru.netology.fmhandroid.exceptions.UnknownException
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Utils {

    fun convertDate(dateTime: LocalDateTime): String {

        val localDateTime = LocalDateTime.parse(dateTime.toString())
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(
            "dd.MM.yyy",
            Locale.getDefault()
        )
        return formatter.format(localDateTime)
    }

    fun convertTime(dateTime: LocalDateTime): String {

        val localDateTime = LocalDateTime.parse(dateTime.toString())
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(
            "HH-mm",
            Locale.getDefault()
        )
        return formatter.format(localDateTime)
    }

    suspend fun <T, R> makeRequest(
        request: suspend () -> Response<T>,
        onSuccess: suspend (body: T) -> R
    ): R {
        try {
            val response = request()
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }
            val body =
                response.body() ?: throw ApiException(response.code(), response.message())
            return onSuccess(body)
        } catch (e: IOException) {
            throw ServerException
        } catch (e: Exception) {
            throw UnknownException
        }
    }

    fun shortUserNameGenerator(firstName: String, lastName: String, middleName: String): String {
        return "$lastName ${firstName.first().uppercase()}. ${middleName.first().uppercase()}."
    }
}