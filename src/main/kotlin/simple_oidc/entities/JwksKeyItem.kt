package com.kbalazsworks.simple_oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class JwksKeyItem (
    @JsonProperty("kty")
    private val kty: String,

    @JsonProperty("use")
    private val use: String,

    @JsonProperty("kid")
    private val kid: String,

    @JsonProperty("e")
    private val e: String,

    @JsonProperty("n")
    private val n: String,

    @JsonProperty("alg")
    private val alg: String
)
