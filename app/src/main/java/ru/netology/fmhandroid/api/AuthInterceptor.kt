package ru.netology.fmhandroid.api

import okhttp3.Interceptor
import okhttp3.Response
import ru.netology.fmhandroid.auth.AppAuth

class AuthInterceptor(private val auth: AppAuth) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = checkNotNull(auth.authState) {
            "User must be authorized"
        }.accessToken

        val newRequest = chain.request().addAuthorizationHeader(token)
        return chain.proceed(newRequest)
    }
}
