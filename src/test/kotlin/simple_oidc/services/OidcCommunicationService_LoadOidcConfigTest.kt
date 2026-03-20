package simple_oidc.services

import com.kbalazsworks.simple_oidc.services.OidcCommunicationService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import simple_oidc.AbstractTest

@Suppress("ClassName")
class OidcCommunicationService_LoadOidcConfigTest : AbstractTest() {
    @Autowired
    private lateinit var oidcCommunicationService: OidcCommunicationService

    @Test
    fun callRealOidcServer() {
        println(oidcCommunicationService.loadOidcConfig())
    }
}
