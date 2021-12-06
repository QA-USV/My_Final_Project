package ru.netology.fmhandroid.api

import okhttp3.Interceptor
import okhttp3.Response
import ru.netology.fmhandroid.auth.AppAuth

class AuthInterceptor(private val auth: AppAuth) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = checkNotNull(
            auth.accessToken
        ) {"Access token should be defined in 'Login' state"}
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}
