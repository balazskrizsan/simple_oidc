package com.kbalazsworks.simple_oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class IntrospectRawResponse(
    @JsonProperty("iss")
    val iss: String?,

    @JsonProperty("nbf")
    val nbf: Int?,

    @JsonProperty("iat")
    val iat: Int?,

    @JsonProperty("exp")
    val exp: Int?,

    @JsonProperty("aud")
    val aud: List<String>,

    @JsonProperty("client_id")
    val clientId: String?,

    @JsonProperty("sub")
    val sub: String?,

    @JsonProperty("auth_time")
    val authTime: Int?,

    @JsonProperty("idp")
    val idp: String?,

    @JsonProperty("amr")
    val amr: String?,

    @JsonProperty("sid")
    val sid: String?,

    @JsonProperty("jti")
    val jti: String?,

    @JsonProperty("active")
    val active: Boolean?,

    @JsonProperty("scope")
    val scope: String?
)
