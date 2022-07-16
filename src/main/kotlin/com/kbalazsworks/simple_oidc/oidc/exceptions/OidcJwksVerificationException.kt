package com.kbalazsworks.simple_oidc.oidc.exceptions

class OidcJwksVerificationException : OidcException {
    constructor() : super()
    constructor(message: String) : super(message)
}
