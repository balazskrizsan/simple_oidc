package com.kbalazsworks.simple_oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class JwksKeys(@JsonProperty("keys") val keys: List<JwksKeyItem>)
