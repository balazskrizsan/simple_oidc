package simple_oidc.services

import com.kbalazsworks.simple_oidc.exceptions.OidcKeyException
import com.kbalazsworks.simple_oidc.services.JwtValidationService
import okio.ByteString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import simple_oidc.AbstractTest
import java.security.PublicKey

@Suppress("ClassName")
class JwtValidationService_getPublicKeyTest : AbstractTest() {
    @Autowired
    lateinit var jwtValidationService: JwtValidationService

    @Test
    fun validKeyData_generatesPublicKey() {
        // Arrange
        val testedModulus =
            "1CtIveaHoNVBda1TVSPWCE8owTaQ1-qSxkkLDqKsuIC_p0ND9RgWlU0fArIa4yGzVNWlHL30jdVUEPVbBltZWjjQe_EYeUN-PB8UivhjdtKC_A6hgB3PjfYnUgpg32hzlpCR2hw8sWZ_VNbsOGrSym8hJ7IgugNcFJ0wKG4KjxhxYWxhNyzwO6bMoF2rxyOouFZ0opeSfzSrZdAxOuuOgmbT1X7hl_k1xvsRRDJHrfu_YV10nMQksoPIXWLvy_XyLz3IuhnWi5mrK20UM3vTWRt6gUcRkTQuEzrPBPkKv5UW4R7aOWjzSbSJ0pqdF7J0aRlMpHXuioUhPZAmI8zZ5Q"
        val testedExponent = "AQAB"

        val expectedAlgorithm = "RSA"
        val expectedFormat = "X.509"
        val expectedToString = """Sun RSA public key, 2048 bits
  params: null
  modulus: 26783864861521073443576794371307653805125482200676057182736355414917798379976885131959420676469889990994214843288903819243756136522709902752731390033385824750164962220547671486137688497654424221783336119631761033615596866293957758502550283207859774245775783128491827072806518065996728796190399331513993964290817921900659626887397624764519873415063410615248840930079003167176875457933430961122573531780448503760788227152482718949267394795445119815903805891051692863774464761165271032933001119409028516061477716038498637599532207203283867735180137244335581706786522362817391654084376110728106618992374030942594501761509
  public exponent: 65537"""
        val expectedEncodedHash = "[hex=bc20b5a69af5a1682e3974b272491c058a58848107eab579376ecac8bc4d5c35]"

        // Act
        val actual: PublicKey = jwtValidationService.getPublicKey(testedModulus, testedExponent)

        // Assert
        org.junit.jupiter.api.Assertions.assertAll(
            Executable { Assertions.assertThat(actual.algorithm).isEqualTo(expectedAlgorithm) },
            Executable { Assertions.assertThat(actual.format).isEqualTo(expectedFormat) },
            Executable {
                Assertions.assertThat(ByteString.of(*actual.encoded).sha256().toString()).isEqualTo(expectedEncodedHash)
            },
            Executable { Assertions.assertThat(actual.toString()).isEqualTo(expectedToString) }
        )
    }

    @Test
    fun invalidKeyData_throwsException() {
        // Arrange
        val testedModulus = "short-modulus"
        val testedExponent = "AQAB"

        val expectedException = OidcKeyException::class.java
        val expectedErrorMessage = "Public key generate error"

        // Act - Assert
        Assertions.assertThatThrownBy {
            jwtValidationService.getPublicKey(
                testedModulus,
                testedExponent
            )
        }
            .isInstanceOf(expectedException)
            .hasMessage(expectedErrorMessage)
    }
}
