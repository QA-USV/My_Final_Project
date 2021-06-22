package ru.netology.fmhandroid.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.fmhandroid.BuildConfig
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    companion object {

        private val okhttp = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        private val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttp)
            .build()

        val retrofit: Retrofit
            get() = retrofitBuilder
    }
}