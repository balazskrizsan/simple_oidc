package simple_oidc.services

import com.kbalazsworks.simple_oidc.exceptions.OidcJwtParseException
import com.kbalazsworks.simple_oidc.services.JwtValidationService
import okio.ByteString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import simple_oidc.AbstractTest

class JwtValidationService_getSignatureTest : AbstractTest() {
    @Autowired
    lateinit var jwtValidationService: JwtValidationService

    @Test
    fun validToken_returnsExpectedSignature() {
        // Arrange
        val testedToken: String = getValidExpiredToken()
        val expectedSignatureHash = "[hex=760a7db56407fed85b79fb140b9cc31f565b743c6ee3c902b9827682caced215]"

        // Act
        val actual: ByteArray = jwtValidationService.getSignature(testedToken)

        // Assert
        Assertions.assertThat(ByteString.of(*actual).sha256().toString()).isEqualTo(expectedSignatureHash)
    }

    @Test
    fun invalidToken_willThrownException() {
        // Arrange
        val testedToken: String = getInvalidToken()
        val expectedException = OidcJwtParseException::class.java
        val expectedErrorMessage = "Signature parse error"

        // Act / Assert
        Assertions.assertThatThrownBy { jwtValidationService.getSignature(testedToken) }
            .isInstanceOf(expectedException)
            .hasMessage(expectedErrorMessage)
    }
}
