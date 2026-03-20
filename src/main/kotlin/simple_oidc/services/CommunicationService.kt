package com.kbalazsworks.simple_oidc.services

import com.kbalazsworks.simple_oidc.entities.AccessTokenRawResponse
import com.kbalazsworks.simple_oidc.entities.grant_type.IGrantType
import com.kbalazsworks.simple_oidc.exceptions.OidcException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CommunicationService(
    private val responseValidatorService: ResponseValidatorService,
    private val grantStoreService: GrantStoreService,
    private val oidcHttpService: OidcHttpService,
    private val oidcCommunicationService: OidcCommunicationService
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Throws(OidcException::class)
    fun callTokenEndpoint(key: String): AccessTokenRawResponse {
        log.info("Call token endpoint with key: {}", key)
        val clientCredential: IGrantType = grantStoreService.getGrant(key)

        return callTokenEndpoint(
            clientCredential.clientId,
            clientCredential.clientSecret,
            clientCredential.getScopeAsString(),
            clientCredential.grantType,
            HashMap()
        )
    }

    @Throws(OidcException::class)
    fun callTokenEndpoint(
        clientId: String,
        clientSecret: String,
        scope: String,
        grantType: String,
        extraParams: Map<String, String>
    ): AccessTokenRawResponse {
        log.info("Call token endpoint with ClientId: {}", clientId)

        val params: MutableMap<String, String> = object : HashMap<String, String>() {
            init {
                put("client_id", clientId)
                put("client_secret", clientSecret)
                put("scope", scope)
                put("grant_type", grantType)
            }
        }
        params.putAll(extraParams)

        val oidcConfig = oidcCommunicationService.loadOidcConfig();

        return responseValidatorService.tokenEndpointValidator(
            oidcHttpService.post(
                oidcConfig.tokenEndpoint!!,
                params,
                AccessTokenRawResponse::class.java
            )
        )
    }
}
