package com.kbalazsworks.simple_oidc.exceptions

class OidcException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}

