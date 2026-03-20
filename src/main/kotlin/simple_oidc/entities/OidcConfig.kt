package com.kbalazsworks.simple_oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class OidcConfig(
    @JsonProperty("issuer")
    val issuer: String?,

    @JsonProperty("jwks_uri")
    val jwksUri: String?,

    @JsonProperty("authorization_endpoint")
    val authorizationEndpoint: String?,

    @JsonProperty("token_endpoint")
    val tokenEndpoint: String?,

    @JsonProperty("userinfo_endpoint")
    val userinfoEndpoint: String?,

    @JsonProperty("end_session_endpoint")
    val endSessionEndpoint: String?,

    @JsonProperty("check_session_iframe")
    val checkSessionIframe: String?,

    @JsonProperty("revocation_endpoint")
    val revocationEndpoint: String?,

    @JsonProperty("introspection_endpoint")
    val introspectionEndpoint: String?,

    @JsonProperty("device_authorization_endpoint")
    val deviceAuthorizationEndpoint: String?,

    @JsonProperty("backchannel_authentication_endpoint")
    val backchannelAuthenticationEndpoint: String?,
)
