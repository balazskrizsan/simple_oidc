package simple_oidc.services

import com.kbalazsworks.simple_oidc.entities.grant_type.ClientCredentials
import com.kbalazsworks.simple_oidc.entities.grant_type.IGrantType
import com.kbalazsworks.simple_oidc.entities.grant_type.TokenExchange
import com.kbalazsworks.simple_oidc.services.GrantStoreService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import simple_oidc.AbstractTest

@Suppress("ClassName")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GrantStoreService_addGrant_getGrantTest : AbstractTest() {
    @Autowired
    private lateinit var grantStoreService: GrantStoreService

    @Test
    fun addGrantToTheStore_returnsTheAddedGrant() {
        // Arrange
        val testedGrant1: IGrantType = ClientCredentials("a", "b", listOf("c"))
        val testedGrantKey1 = "test1"
        val testedGrant2: IGrantType = TokenExchange("q", "w", listOf("e"))
        val testedGrantKey2 = "test2"
        val expectedGrant1: IGrantType = ClientCredentials("a", "b", listOf("c"))
        val expectedGrant2: IGrantType = TokenExchange("q", "w", listOf("e"))

        // Act
        grantStoreService.addGrant(testedGrantKey1, testedGrant1)
        grantStoreService.addGrant(testedGrantKey2, testedGrant2)
        val actual1: IGrantType = grantStoreService.getGrant(testedGrantKey1)
        val actual2: IGrantType = grantStoreService.getGrant(testedGrantKey2)

        // Assert
        assertAll(
            Executable { assertThat(actual1).usingRecursiveComparison().isEqualTo(expectedGrant1) },
            Executable { assertThat(actual1.grantType).isEqualTo(expectedGrant1.grantType) },
            Executable { assertThat(actual1.getScopeAsString()).isEqualTo(expectedGrant1.getScopeAsString()) },
            Executable { assertThat(actual2).usingRecursiveComparison().isEqualTo(expectedGrant2) },
            Executable { assertThat(actual2.grantType).isEqualTo(expectedGrant2.grantType) },
            Executable { assertThat(actual2.getScopeAsString()).isEqualTo(expectedGrant2.getScopeAsString()) }
        )
    }
}