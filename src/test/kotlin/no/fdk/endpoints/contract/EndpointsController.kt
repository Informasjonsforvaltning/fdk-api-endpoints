package no.fdk.endpoints.contract

import no.fdk.endpoints.utils.ApiTestContext
import no.fdk.endpoints.utils.apiGet
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
    properties = ["spring.profiles.active=contract-test"],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Tag("contract")
class EndpointsController : ApiTestContext() {

    @Nested
    internal inner class Environment {

        @Test
        fun okForTest() {
            val rsp = apiGet("/endpoints?environment=test", emptyMap())

            assertEquals(HttpStatus.OK.value(), rsp["status"])
        }

        @Test
        fun okForProduction() {
            val rsp = apiGet("/endpoints?environment=production", emptyMap())

            assertEquals(HttpStatus.OK.value(), rsp["status"])
        }

        @Test
        fun badRequestForNonEnumValues() {
            val rspNull = apiGet("/endpoints", emptyMap())
            val rspNumber = apiGet("/endpoints?environment=132", emptyMap())
            val rspString = apiGet("/endpoints?environment=asd", emptyMap())

            assertEquals(HttpStatus.BAD_REQUEST.value(), rspNull["status"])
            assertEquals(HttpStatus.BAD_REQUEST.value(), rspNumber["status"])
            assertEquals(HttpStatus.BAD_REQUEST.value(), rspString["status"])
        }

    }

}