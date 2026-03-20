package com.kbalazsworks.simple_oidc.services

import com.kbalazsworks.simple_oidc.entities.AccessTokenRawResponse
import com.kbalazsworks.simple_oidc.entities.IntrospectRawResponse
import com.kbalazsworks.simple_oidc.entities.JwksKeys
import com.kbalazsworks.simple_oidc.exceptions.OidcApiException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ResponseValidatorService {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Throws(OidcApiException::class)
    fun tokenEndpointValidator(response: AccessTokenRawResponse): AccessTokenRawResponse {
        if (null == response.accessToken || null == response.expiresIn || null == response.tokenType) {
            log.error("Token endpoint invalid response: {}", response)

            throw OidcApiException("Token endpoint invalid response")
        }

        if (null == response.scope) {
            log.warn("Token endpoint response has no scope: {}", response)
        }

        return response
    }

    @Throws(OidcApiException::class)
    fun introspectEndpointValidator(response: IntrospectRawResponse): IntrospectRawResponse {
        if (null == response.active) {
            log.error("Token endpoint invalid response: {}", response)

            throw OidcApiException("Token endpoint invalid response")
        }


        return response
    }

    @Throws(OidcApiException::class)
    fun jwksEndpointValidator(response: JwksKeys): JwksKeys {
        if (response.keys.isEmpty()) {
            log.error("Token endpoint invalid response: {}", response)

            throw OidcApiException("Token endpoint invalid response")
        }

        return response
    }
}
