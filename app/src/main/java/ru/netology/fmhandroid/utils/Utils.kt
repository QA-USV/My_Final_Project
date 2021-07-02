package ru.netology.fmhandroid.utils

import retrofit2.Response
import ru.netology.fmhandroid.exceptions.ApiException
import ru.netology.fmhandroid.exceptions.ServerException
import ru.netology.fmhandroid.exceptions.UnknownException
import java.io.IOException

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