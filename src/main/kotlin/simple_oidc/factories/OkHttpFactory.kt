package com.kbalazsworks.simple_oidc.factories

import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class OkHttpFactory {
    companion object {
        private val TRUST_ALL_CERTS: X509TrustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }

    fun createOkHttpClient(isHttps: Boolean = true): OkHttpClient {
        val sslContext = SSLContext.getInstance("SSL")
        if (isHttps) {
            sslContext.init(null, arrayOf<TrustManager>(TRUST_ALL_CERTS), SecureRandom())
        }

        var builder = OkHttpClient().newBuilder()
        if (isHttps) {
            builder = builder.sslSocketFactory(sslContext.socketFactory, TRUST_ALL_CERTS)
        }

        builder = builder
            .callTimeout(10000, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)

        return builder.build()
    }
}
