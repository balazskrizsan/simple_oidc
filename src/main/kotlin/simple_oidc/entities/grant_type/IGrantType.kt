package com.kbalazsworks.simple_oidc.entities.grant_type

interface IGrantType {
    val clientId: String
    val clientSecret: String
    val grantType: String
    fun getScopeAsString(): String
}
