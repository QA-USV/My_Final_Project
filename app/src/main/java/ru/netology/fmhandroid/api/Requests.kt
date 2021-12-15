package ru.netology.fmhandroid.api

import okhttp3.Request

internal fun Request.addAuthorizationHeader(token: String) =
    newBuilder()
        .addHeader("Authorization", token)
        .build()
