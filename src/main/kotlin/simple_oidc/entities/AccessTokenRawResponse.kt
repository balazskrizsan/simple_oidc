package com.kbalazsworks.simple_oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenRawResponse(
    @JsonProperty("access_token")
    val accessToken: String?,

    @JsonProperty("expires_in")
    val expiresIn: Int?,

    @JsonProperty("token_type")
    val tokenType: String?,

    @JsonProperty("scope")
    val scope: String?
)
