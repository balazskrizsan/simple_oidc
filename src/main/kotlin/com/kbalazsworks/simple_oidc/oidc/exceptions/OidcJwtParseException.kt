package com.kbalazsworks.simple_oidc.oidc.exceptions

class OidcJwtParseException : OidcException {
    constructor() : super()
    constructor(message: String) : super(message)
}
