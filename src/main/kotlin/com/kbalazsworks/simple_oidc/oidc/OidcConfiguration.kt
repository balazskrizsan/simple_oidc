package com.kbalazsworks.simple_oidc.oidc

import com.kbalazsworks.simple_oidc.oidc.factories.OidcServiceFactory
import com.kbalazsworks.simple_oidc.oidc.services.OidcService
import io.quarkus.arc.DefaultBean
import javax.enterprise.context.Dependent
import javax.enterprise.inject.Produces

@Dependent
class OidcConfiguration(private val oidcServiceFactory: OidcServiceFactory) {
    @Produces
    @DefaultBean
    fun oidcService(): OidcService {
        return oidcServiceFactory.create("https://localhost:5001")
    }
}
