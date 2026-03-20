package simple_oidc.services

import com.kbalazsworks.simple_oidc.entities.grant_type.ClientCredentials
import com.kbalazsworks.simple_oidc.services.GrantStoreService
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import simple_oidc.AbstractTest

@Suppress("ClassName")
class GrantStoreService_protectStoreTest : AbstractTest() {
    @Autowired
    lateinit var grantStoreService: GrantStoreService

    @Test
    fun addItemToProtectedStore_throwsException() {
        // Arrange
        grantStoreService.protectStore()
        val testedGrant = ClientCredentials("a", "b", listOf("c"))
        val testedGrantKey = "test123"

        // Act / Assert
        assertThatThrownBy { grantStoreService.addGrant(testedGrantKey, testedGrant) }.isInstanceOf(
            UnsupportedOperationException::class.java
        )
    }
}