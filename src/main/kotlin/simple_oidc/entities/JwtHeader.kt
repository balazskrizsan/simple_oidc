package com.kbalazsworks.simple_oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

class JwtHeader(
    @JsonProperty("alg")
    val alg: String,

    @JsonProperty("kid")
    val kid: String,

    @JsonProperty("typ")
    val typ: String,
)
