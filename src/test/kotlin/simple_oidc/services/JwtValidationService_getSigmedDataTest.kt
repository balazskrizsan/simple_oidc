package simple_oidc.services


import com.kbalazsworks.simple_oidc.exceptions.OidcJwtParseException
import com.kbalazsworks.simple_oidc.services.JwtValidationService
import okio.ByteString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import simple_oidc.AbstractTest

@Suppress("ClassName")
class JwtValidationService_getSigmedDataTest : AbstractTest() {
    @Autowired
    lateinit var jwtValidationService: JwtValidationService

    @Test
    fun validToken_returnsExpectedSigedData() {
        // Arrange
        val testedToken: String = getValidExpiredToken()
        val expectedSignatureHash = "[hex=29a232cc7d06af87bba8678996ea31208bb34a461e5f78c63c097ea4f07cbab5]"

        // Act
        val actual: ByteArray = jwtValidationService.getSignedData(testedToken)

        // Assert
        Assertions.assertThat(ByteString.of(*actual).sha256().toString()).isEqualTo(expectedSignatureHash)
    }

    @Test
    fun invalidToken_willThrownException() {
        // Arrange
        val testedToken: String = getInvalidToken()
        val expectedException = OidcJwtParseException::class.java
        val expectedErrorMessage = "Signed data parse error"

        // Act / Assert
        Assertions.assertThatThrownBy { jwtValidationService.getSignedData(testedToken) }
            .isInstanceOf(expectedException)
            .hasMessage(expectedErrorMessage)
    }
}
