package com.kbalazsworks.simple_oidc.oidc.services

import com.kbalazsworks.simple_oidc.oidc.entities.JwksKeys

interface IOidcService {
    fun callJwksEndpoint(): JwksKeys
    fun isJwksVerifiedToken(token: String): Boolean
    fun checkJwksVerifiedToken(token: String)
    fun isExpiredToken(token: String): Boolean
    fun checkExpiredToken(token: String)
    fun checkValidated(token: String)
    fun hasScopesInToken(token: String, scopes: List<String>): Boolean
    fun checkScopesInToken(token: String, scopes: List<String>)
}
