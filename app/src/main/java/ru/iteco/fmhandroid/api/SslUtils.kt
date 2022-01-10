package ru.iteco.fmhandroid.api

import java.io.FileInputStream
import java.io.InputStream
import java.lang.Exception
import java.lang.RuntimeException
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


object SslUtils {
    fun getSslContextForCertificateFile(fileName: String): SSLContext {
        return try {
            val keyStore = getKeyStore(fileName)
            val sslContext = SSLContext.getInstance("SSL")
            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
            sslContext
        } catch (e: Exception) {
            val msg = "Cannot load certificate from file"
            throw RuntimeException(msg)
        }
    }

    private fun getKeyStore(fileName: String): KeyStore? {
        var keyStore: KeyStore? = null
        try {
            val cf = CertificateFactory.getInstance("X.509")
            val inputStream: InputStream = FileInputStream(fileName)
            val ca: Certificate
            try {
                ca = cf.generateCertificate(inputStream)
            } finally {
                inputStream.close()
            }
            val keyStoreType = KeyStore.getDefaultType()
            keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return keyStore
    }
}