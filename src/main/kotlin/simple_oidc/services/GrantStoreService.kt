package com.kbalazsworks.simple_oidc.services

import com.kbalazsworks.simple_oidc.entities.grant_type.IGrantType
import com.kbalazsworks.simple_oidc.exceptions.OidcException
import org.springframework.stereotype.Service
import java.util.*

@Service
class GrantStoreService {
    private var clientCredentialsState: MutableMap<String, IGrantType> = HashMap()

    fun addGrant(key: String, grant: IGrantType) {
        clientCredentialsState[key] = grant
    }

    @Throws(OidcException::class)
    fun getGrant(key: String) = Objects.requireNonNull(clientCredentialsState[key])
        ?: throw OidcException("No grant found for key: $key")

    fun protectStore() {
        clientCredentialsState = Collections.unmodifiableMap(clientCredentialsState)
    }
}