package com.kbalazsworks.simple_oidc.services

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.kbalazsworks.simple_oidc.entities.JwtData
import com.kbalazsworks.simple_oidc.entities.JwtHeader
import com.kbalazsworks.simple_oidc.exceptions.OidcJwtParseException
import com.kbalazsworks.simple_oidc.exceptions.OidcKeyException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.math.BigInteger
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPublicKeySpec
import java.util.*

@Service
class JwtValidationService {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private val objectMapper: ObjectMapper = ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Throws(OidcJwtParseException::class)
    fun getJwtData(token: String): JwtData {
        try {
            return getJwtDataLogic(token)
        } catch (e: Exception) {
            log.error("JWT Data parse error: {}", e.message)

            throw OidcJwtParseException("JWT Data parse error")
        }
    }

    @Throws(IOException::class)
    private fun getJwtDataLogic(token: String): JwtData {
        val tokenParts = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val dataPart = tokenParts[1].toByteArray()
        val decodedJwtData = Base64.getDecoder().decode(dataPart)

        return objectMapper.readValue<JwtData>(decodedJwtData, JwtData::class.java)
    }

    @Throws(OidcJwtParseException::class)
    fun getJwtHeader(token: String): JwtHeader {
        try {
            return getJwtHeaderLogic(token)
        } catch (e: Exception) {
            log.error("JWT Header parse error: {}", e.message)

            throw OidcJwtParseException("JWT Header parse error")
        }
    }

    @Throws(IOException::class, OidcJwtParseException::class)
    private fun getJwtHeaderLogic(token: String): JwtHeader {
        checkValidTokenFormat(token)

        val tokenParts = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val dataPart = tokenParts[0].toByteArray()
        val decodedJwtHeader = Base64.getDecoder().decode(dataPart)

        return objectMapper.readValue(decodedJwtHeader, JwtHeader::class.java)
    }

    @Throws(OidcKeyException::class)
    fun getPublicKey(modulus: String, exponent: String): PublicKey {
        try {
            return getPublicKeyLogic(modulus, exponent)
        } catch (e: IllegalArgumentException) {
            log.error("Public key generate error: {}", e.message)

            throw OidcKeyException("Public key generate error")
        } catch (e: InvalidKeySpecException) {
            log.error("Public key generate error: {}", e.message)

            throw OidcKeyException("Public key generate error")
        } catch (e: NoSuchAlgorithmException) {
            log.error("Public key generate error: {}", e.message)

            throw OidcKeyException("Public key generate error")
        }
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    private fun getPublicKeyLogic(modulus: String, exponent: String): PublicKey {
        val decoder = Base64.getUrlDecoder()

        val exponentB = decoder.decode(exponent)
        val modulusB = decoder.decode(modulus)
        val bigExponent = BigInteger(1, exponentB)
        val bigModulus = BigInteger(1, modulusB)

        return KeyFactory.getInstance("RSA").generatePublic(RSAPublicKeySpec(bigModulus, bigExponent))
    }

    @Throws(OidcJwtParseException::class)
    fun getSignature(token: String): ByteArray {
        try {
            return getSignatureLogic(token)
        } catch (e: Exception) {
            log.error("Signature parse error: {}", e.message)

            throw OidcJwtParseException("Signature parse error")
        }
    }

    @Throws(OidcJwtParseException::class)
    private fun getSignatureLogic(token: String): ByteArray {
        checkValidTokenFormat(token)

        val signatureB64u = token.substring(token.lastIndexOf(".") + 1)

        return Base64.getUrlDecoder().decode(signatureB64u)
    }

    @Throws(OidcJwtParseException::class)
    fun getSignedData(token: String): ByteArray {
        try {
            checkValidTokenFormat(token)

            return token.substring(0, token.lastIndexOf(".")).toByteArray()
        } catch (e: Exception) {
            log.error("Signed data parse error: {}", e.message)

            throw OidcJwtParseException("Signed data parse error")
        }
    }

    @Throws(OidcJwtParseException::class)
    private fun checkValidTokenFormat(token: String) {
        val tokenLength = token.replace("[^.]".toRegex(), "").length
        if (tokenLength != 2) {
            throw OidcJwtParseException("Number of the points in token is: $tokenLength")
        }
    }
}