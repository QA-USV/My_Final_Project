package ru.iteco.fmhandroid.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.iteco.fmhandroid.BuildConfig
import ru.iteco.fmhandroid.api.qualifier.Authorized
import ru.iteco.fmhandroid.api.qualifier.NonAuthorized
import ru.iteco.fmhandroid.api.qualifier.Refresh
import ru.iteco.fmhandroid.auth.AppAuth
import ru.iteco.fmhandroid.repository.authRepository.AuthRepository
import javax.inject.Provider
import java.security.cert.X509Certificate
import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.decodeCertificatePem

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private val certificate: X509Certificate = ("-----BEGIN CERTIFICATE-----\n" +
            "MIIDmTCCAoGgAwIBAgIUIekvE6KADZePUtq8XQ2DSih+W9QwDQYJKoZIhvcNAQEL\n" +
            "BQAwfDELMAkGA1UEBhMCWFgxDDAKBgNVBAgMA04vQTEMMAoGA1UEBwwDTi9BMSAw\n" +
            "HgYDVQQKDBdTZWxmLXNpZ25lZCBjZXJ0aWZpY2F0ZTEvMC0GA1UEAwwmMTMwLjE5\n" +
            "My40NC45NjogU2VsZi1zaWduZWQgY2VydGlmaWNhdGUwHhcNMjExMjE0MTc0OTM2\n" +
            "WhcNMjMxMjE0MTc0OTM2WjB8MQswCQYDVQQGEwJYWDEMMAoGA1UECAwDTi9BMQww\n" +
            "CgYDVQQHDANOL0ExIDAeBgNVBAoMF1NlbGYtc2lnbmVkIGNlcnRpZmljYXRlMS8w\n" +
            "LQYDVQQDDCYxMzAuMTkzLjQ0Ljk2OiBTZWxmLXNpZ25lZCBjZXJ0aWZpY2F0ZTCC\n" +
            "ASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALDv+ZFieSniXLjwmSaFtsfZ\n" +
            "WIrSx0AgLGA2efz2LXLuvH14o2gekMY0mqekn3SpuaVcvHfU17kx3QLtiB286Bkv\n" +
            "a3VfISCVL8qZhTolSWDNyyeU9dZqXZ81hsxXrCZNvGkGtgI5vY74PgpbUaLWPnzW\n" +
            "fXITgEqyTf4OLvORufKWeelZnioDlr7WJGtZhCff2f5Y+WuWtN3UNVVGmCEB1LwB\n" +
            "7IoyHOdhDUXOjqkFwes1qSmTqCKAnfdGfMdluPte4BrTPCV/dI0Jckdid489brLm\n" +
            "r5f7dbZmBpR4yoBrT1oq1yb56+jmI+utCmUhuhR8k3IN91t9i6Mn4dEtHFZ+5UEC\n" +
            "AwEAAaMTMBEwDwYDVR0RBAgwBocEgsEsYDANBgkqhkiG9w0BAQsFAAOCAQEAJqt9\n" +
            "JBq5JAdlwF1mbFN9FYGiul4OjbklX7YoQyKJ1Fo3c1NgVLpHNHDg6XwWRGS8eakA\n" +
            "O3EochdghTRvxT2jULzJfEO/g/BfqUHs6wpS0RSAX92ibfoxlUdurjLGKTww+lfx\n" +
            "5B3vTY/xuJG6uqTBgQCqRjyg7F0iWCFtmCgE4WLFH5rQVWAvM5DRlYDnedL42jMG\n" +
            "A2AVYVptHELVYe0eMiyLnltITIqin9ti2LEAnpcor8CqYp9wcxQPXmQ7hG3x8rW9\n" +
            "tnscoka2qxFjBMqcmGm4Q7yl62nJYNXp5lihXlgjwy+BZPo7h8EWqs6kfmMpKjG8\n" +
            "AS0Wp/MyXT/+rhBblw==\n" +
            "-----END CERTIFICATE-----").decodeCertificatePem()

    private val handshakeCertificate: HandshakeCertificates = HandshakeCertificates.Builder()
        .addTrustedCertificate(certificate)
        .build()

    @Provides
    fun loggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

    @NonAuthorized
    @Provides
    fun provideNonAuthorizedRetrofit(@NonAuthorized client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()

    @Authorized
    @Provides
    fun provideAuthorizedRetrofit(@Authorized client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .build()

    @Authorized
    @Provides
    fun authorizedOkhttp(
        interceptor: HttpLoggingInterceptor,
        appAuth: AppAuth,
        authRepositoryProvider: Provider<AuthRepository>
    ): OkHttpClient {
        val authInterceptor = AuthInterceptor(appAuth)
        val refreshAuthenticator = RefreshAuthenticator(authRepositoryProvider, appAuth)
        return OkHttpClient.Builder()
            .sslSocketFactory(
                handshakeCertificate.sslSocketFactory(),
                handshakeCertificate.trustManager
            )
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .authenticator(refreshAuthenticator)
            .build()
    }

    @NonAuthorized
    @Provides
    fun nonAuthorizedOkhttp(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .sslSocketFactory(
                handshakeCertificate.sslSocketFactory(),
                handshakeCertificate.trustManager
            )
            .addInterceptor(interceptor)
            .build()
    }

    @Refresh
    @Provides
    fun refreshOkhttp(interceptor: HttpLoggingInterceptor, appAuth: AppAuth): OkHttpClient {
        val refreshInterceptor = RefreshInterceptor(appAuth)
        return OkHttpClient.Builder()
            .sslSocketFactory(
                handshakeCertificate.sslSocketFactory(),
                handshakeCertificate.trustManager
            )
            .addInterceptor(refreshInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Refresh
    @Provides
    fun provideRefreshRetrofit(@Refresh client: OkHttpClient): Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()

}