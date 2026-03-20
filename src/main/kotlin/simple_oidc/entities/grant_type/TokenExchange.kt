package com.kbalazsworks.simple_oidc.entities.grant_type

import com.kbalazsworks.simple_oidc.enums.GrantTypeEnum

data class TokenExchange(
    override val clientId: String,
    override val clientSecret: String,
    val scope: List<String>,
    override val grantType: String = GrantTypeEnum.TOKEN_EXCHANGE.value,
    val customParameters: MutableMap<String, String> = mutableMapOf()
) : IGrantType {

    override fun getScopeAsString(): String = scope.joinToString(" ")
}
