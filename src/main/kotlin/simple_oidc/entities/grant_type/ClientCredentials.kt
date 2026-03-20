package com.kbalazsworks.simple_oidc.entities.grant_type

import com.kbalazsworks.simple_oidc.enums.GrantTypeEnum

data class ClientCredentials(
    override val clientId: String,
    override val clientSecret: String,
    val scope: List<String>
) : IGrantType {
    override val grantType: String = GrantTypeEnum.CLIENT_CREDENTIALS.value
    override fun getScopeAsString(): String = scope.joinToString(" ")
}
