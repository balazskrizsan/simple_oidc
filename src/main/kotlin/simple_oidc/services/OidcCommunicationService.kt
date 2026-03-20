package com.kbalazsworks.simple_oidc.services

import com.kbalazsworks.simple_oidc.ApplicationProperties
import com.kbalazsworks.simple_oidc.entities.OidcConfig
import com.kbalazsworks.simple_oidc.exceptions.OidcException
import org.springframework.stereotype.Service
import java.util.*

@Service
class OidcCommunicationService(
    private val oidcHttpService: OidcHttpService,
    private val applicationProperties: ApplicationProperties
) {
    private var configPath = applicationProperties.identityServerHost + applicationProperties.openidConfigurationUri;

    fun loadOidcConfig() = Objects.requireNonNull(oidcHttpService.get(configPath, OidcConfig::class.java))
        ?: throw OidcException("Could not load OidcConfig from $configPath")
}
