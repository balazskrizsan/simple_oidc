package com.kbalazsworks.simple_oidc

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "simple-oidc")
open class ApplicationProperties {
    lateinit var identityServerHost: String
    var openidConfigurationUri: String = "/.well-known/openid-configuration"
}
