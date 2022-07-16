package com.kbalazsworks.simple_oidc.common.factories

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemFactory {
    fun getCurrentTimeMillis() = System.currentTimeMillis()
}
