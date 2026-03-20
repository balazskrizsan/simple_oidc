package simple_oidc.services

import com.kbalazsworks.simple_oidc.entities.AccessTokenRawResponse
import com.kbalazsworks.simple_oidc.services.CommunicationService
import com.kbalazsworks.simple_oidc.entities.JwtData
import com.kbalazsworks.simple_oidc.entities.JwtHeader
import com.kbalazsworks.simple_oidc.entities.grant_type.ClientCredentials
import com.kbalazsworks.simple_oidc.services.GrantStoreService
import com.kbalazsworks.simple_oidc.services.JwtValidationService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import simple_oidc.AbstractTest

@Suppress("ClassName")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommunicationService_callTokenEndpointTest : AbstractTest() {
    @Autowired
    lateinit var grantStoreService: GrantStoreService

    @Autowired
    lateinit var communicationService: CommunicationService

    @Autowired
    lateinit var jwtValidationService: JwtValidationService

    @Test
    fun storeAndRequestTokenWithGrantStore_returnsValidJwt() {
        // Arrange
        val expectedAlg = "RS256"
        val expectedTyp = "at+jwt"
        val expectedClientId = "client1_client_credentials"
        val expectedScopeAsList = listOf("test_scope", "test_scope.a")
        val expectedIss = "e2e.test"
        val expectedAud = listOf("test_resource_a", "e2e.test/resources")
        val expectedExpiresIn = 3600
        val expectedTokenType = "Bearer"
        val expectedScope = "test_scope test_scope.a"

        grantStoreService.addGrant(
            "test1", ClientCredentials(
                "client1_client_credentials",
                "client1_client_credentials_secret",
                listOf("test_scope", "test_scope.a")
            )
        )
        grantStoreService.protectStore()

        // Act
        val actual: AccessTokenRawResponse = communicationService.callTokenEndpoint("test1")

        // Assert
        val jwtData: JwtData = jwtValidationService.getJwtData(actual.accessToken!!)
        val jwtHeader: JwtHeader = jwtValidationService.getJwtHeader(actual.accessToken!!)

        assertAll(
            Executable { assertThat(jwtHeader.alg).isEqualTo(expectedAlg) },
            Executable { assertThat(jwtHeader.typ).isEqualTo(expectedTyp) },
            Executable { assertThat(jwtData.clientId).isEqualTo(expectedClientId) },
            Executable { assertThat(jwtData.scope).isEqualTo(expectedScopeAsList) },
            Executable { assertThat(jwtData.iss).isEqualTo(expectedIss) },
            Executable { assertThat(jwtData.aud).isEqualTo(expectedAud) },
            Executable { assertThat(jwtData.exp - jwtData.iat).isEqualTo(expectedExpiresIn) },
            Executable { assertThat(actual.expiresIn).isEqualTo(expectedExpiresIn) },
            Executable { assertThat(actual.tokenType).isEqualTo(expectedTokenType) },
            Executable { assertThat(actual.scope).isEqualTo(expectedScope) }
        )
    }
}
