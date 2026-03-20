package simple_oidc

import com.kbalazsworks.simple_oidc.SpringConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [SpringConfig::class])
@TestPropertySource("classpath:application.properties")
abstract class AbstractTest {
    /**
     * {
     * "alg": "RS256",
     * "kid": "4B9CC612EBE61D72BE1B2CEE8DDAE495",
     * "typ": "at+jwt"
     * }
     * .
     * {
     * "iss": "https://localhost:5001",
     * "nbf": 1659100113,
     * "iat": 1659100113,
     * "exp": 1659103713,
     * "aud": [
     * "sj_aws",
     * "https://localhost:5001/resources"
     * ],
     * "scope": [
     * "sj",
     * "sj.aws.ec2.upload_company_logo",
     * "sj.aws.ses.send_mail"
     * ],
     * "client_id": "sj.aws",
     * "jti": "84FC5DF245FCEF80EA7513A2872711D4"
     * }
     */
    fun getValidExpiredToken() = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjRCOUNDNjEyRUJFNjFENzJCRTFCMkNFRThEREFFNDk1IiwidHlwIjoiYXQrand0In0.eyJpc3MiOiJodHRwczovL2xvY2FsaG9zdDo1MDAxIiwibmJmIjoxNjU5MTMwNDY3LCJpYXQiOjE2NTkxMzA0NjcsImV4cCI6MTY1OTEzNDA2NywiYXVkIjpbInNqX2F3cyIsImh0dHBzOi8vbG9jYWxob3N0OjUwMDEvcmVzb3VyY2VzIl0sInNjb3BlIjpbInNqIiwic2ouYXdzLmVjMi51cGxvYWRfY29tcGFueV9sb2dvIiwic2ouYXdzLnNlcy5zZW5kX21haWwiXSwiY2xpZW50X2lkIjoic2ouYXdzIiwianRpIjoiNDE1Rjg4NEIwRkQ4RkFGMjc4Mzg0NzVEMjUxRDhFOTUifQ.UlGdTWiLBthB9pEf0SW_Vb5RQgjzTJnkaNUB0hid0jvOj5R4XUfXreX13SNIx1mGYDCkqePNbS-CuCJjDC7B2nra-o7wfyNO6lMPrGSBqgHzvQ3H8_1KPEi0Fmp-ZSxJ5oKZR0KS3URqNOEv0xmNOpQP3u2tUApOR9L8G_50C-sb6o2nW74JfsiQq0-Jm35vBU8VO_UBdEPC8v-WbwbmM6ptb9_Uys7vyeUWBqSrEygu7NkmrPRJoeNNjM1L42PSSX1WQ0rXCOfrpa2GeczJQl2RTfBnqU8jdkHDZq8DrZM5ViHV6d7XzlZKQ0WHTKutK5hbsl_SpGzTX9_tqdJaeg"

    fun getInvalidToken() = "in-valid-token"
}
