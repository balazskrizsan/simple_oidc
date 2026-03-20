package com.kbalazsworks.simple_oidc.services

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.kbalazsworks.simple_oidc.entities.BasicAuth
import com.kbalazsworks.simple_oidc.exceptions.OidcApiException
import com.kbalazsworks.simple_oidc.exceptions.OidcException
import org.slf4j.LoggerFactory
import okhttp3.Credentials.basic
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service

@Service
class OidcHttpService(private val okHttpClient: OkHttpClient) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        val objectMapper: ObjectMapper =
            ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    // @todo: test
    @Throws(OidcException::class)
    fun <T> get(url: String, mapperClass: Class<T>) = get(url, HashMap(), HashMap(), mapperClass)

    // @todo: test
    @Throws(OidcException::class)
    fun <T> get(url: String, queryParams: Map<String, String>, headers: Map<String, String>, mapperClass: Class<T>): T {
        val urlBuilder: HttpUrl.Builder = getBuilder(url)

        if (queryParams.isNotEmpty()) {
            queryParams.forEach { (name: String, value: String) -> urlBuilder.addQueryParameter(name, value) }
        }

        val builtUrl = urlBuilder.build().toString()
        val requestBuilder = Request.Builder().url(builtUrl).get()

        if (headers.isNotEmpty()) {
            headers.forEach { (name: String, value: String) -> requestBuilder.addHeader(name, value) }
        }

        return try {
            val body: String = okHttpClient.newCall(requestBuilder.build()).execute().body!!.string()

            objectMapper.readValue(body, mapperClass)
        } catch (e: Exception) {
            log.error("GET Response error: {}", e.message, e)

            throw OidcApiException("GET Response error", e)
        }
    }

    // @todo: test
    @Throws(OidcApiException::class)
    fun <T> post(url: String, postData: Map<String, String>, mapperClass: Class<T>): T {
        return post(url, postData, mapperClass, null)
    }

    // @todo: test
    @Throws(OidcApiException::class)
    fun <T> post(url: String, postData: Map<String, String>, mapperClass: Class<T>, basicAuth: BasicAuth?): T {
        val formBodyBuilder = FormBody.Builder()

        if (postData.isNotEmpty()) {
            postData.forEach { (name: String, value: String) -> formBodyBuilder.add(name, value) }
        }

        val requestBuilder = Request.Builder()
            .url(url)
            .post(formBodyBuilder.build())
            .header("Content-Type", "application/x-www-form-urlencoded")

        if (null != basicAuth) {
            requestBuilder.header(
                "Authorization",
                basic(basicAuth.userName, basicAuth.password)
            )
        }

        val request = requestBuilder.build()

        return try {
            val body: String = okHttpClient.newCall(request).execute().body!!.string()

            objectMapper.readValue(body, mapperClass)
        } catch (e: java.lang.Exception) {
            log.error("GET Response error: {}", e.message, e)

            throw OidcApiException("GET Response error")
        }
    }

    @Throws(OidcException::class)
    private fun getBuilder(url: String): HttpUrl.Builder {
        return try {
            url.toHttpUrlOrNull()!!.newBuilder()
        } catch (e: Exception) {
            log.error("HTTP client creation errror: {}", e.message, e)

            throw OidcException("HTTP client creation error: " + e.message, e)
        }
    }
}
