package com.kbalazsworks.simple_oidc.oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class JwksKeys (
    @JsonProperty("keys")
    val keys: List<JwksKeyItem>
)
