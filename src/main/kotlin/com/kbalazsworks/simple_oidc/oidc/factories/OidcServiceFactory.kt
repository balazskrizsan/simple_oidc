package com.kbalazsworks.simple_oidc.oidc.factories

import com.kbalazsworks.simple_oidc.common.factories.SystemFactory
import com.kbalazsworks.simple_oidc.oidc.entities.OidcConfig
import com.kbalazsworks.simple_oidc.oidc.services.OidcHttpClientService
import com.kbalazsworks.simple_oidc.oidc.services.OidcService
import com.kbalazsworks.simple_oidc.oidc.services.TokenService
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OidcServiceFactory(
    private val oidcHttpClientService: OidcHttpClientService,
    private val tokenService: TokenService,
    private val systemFactory: SystemFactory,
) {
    companion object {
        private const val DISCOVERY_ENDPOINT = "/.well-known/openid-configuration"
    }

    fun create(host: String): OidcService {
        val oidcConfig = oidcHttpClientService.getWithMap(host + DISCOVERY_ENDPOINT, OidcConfig::class.java)

        return OidcService(oidcConfig, oidcHttpClientService, tokenService, systemFactory)
    }
}
