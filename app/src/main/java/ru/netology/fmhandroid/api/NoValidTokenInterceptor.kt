package ru.netology.fmhandroid.api

import okhttp3.Interceptor
import okhttp3.Response
import ru.netology.fmhandroid.auth.AppAuth

class NoValidTokenInterceptor(private val appAuth: AppAuth) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)
        // Истекло время жизни refresh token
        if (response.code == 401) {
            appAuth.createEventFromServerError()
        }
        return response
    }
}