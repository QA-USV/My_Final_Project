package ru.netology.fmhandroid.api

import okhttp3.Interceptor
import okhttp3.Response

private const val responseMessage = "ERR_INVALID_REFRESH"

class NoValidTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)
        return if (response.code == 403 && response.message.equals(responseMessage)) {

        } else {
            response
        }
    }
}