package ru.netology.fmhandroid.api

import okhttp3.Interceptor
import okhttp3.Response
import ru.netology.fmhandroid.auth.AppAuth

private const val responseMessage = "ERR_INVALID_REFRESH"

class NoValidTokenInterceptor(private val appAuth: AppAuth) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code == 403) {
            appAuth.createEventFromServerError()
        }
        return response
    }
}