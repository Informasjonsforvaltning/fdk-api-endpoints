package no.fdk.endpoints.contract

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import no.fdk.endpoints.model.Endpoints
import no.fdk.endpoints.utils.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
    properties = ["spring.profiles.active=contract-test", "application.namespace=staging"],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("contract")
class StagingEndpoints : ApiTestContext() {

    @LocalServerPort
    var port: Int = 0

    @Nested
    internal inner class Environment {

        @Test
        fun allTestEndpoints() {
            val rsp = apiGet("/endpoints?environment=test", emptyMap(), port)

            assertEquals(HttpStatus.OK.value(), rsp["status"])

            val expected = listOf(TEST_0, TEST_1, TEST_2)
            val result: Endpoints = jacksonObjectMapper().readValue(rsp["body"] as String)

            assertEquals(expected.sortedBy { it.apiRef }, result.endpoints.sortedBy { it.apiRef })
        }

        @Test
        fun emptyListForProduction() {
            val rsp = apiGet("/endpoints?environment=production", emptyMap(), port)
            assertEquals(HttpStatus.OK.value(), rsp["status"])

            val expected = Endpoints(emptyList(), 0)
            val result: Endpoints = jacksonObjectMapper().readValue(rsp["body"] as String)

            assertEquals(expected, result)
        }

        @Test
        fun filterByOrgNumbers() {
            val rsp = apiGet("/endpoints?environment=test&orgNos=123456789,987654321", emptyMap(), port)
            assertEquals(HttpStatus.OK.value(), rsp["status"])

            val expected = listOf(TEST_0, TEST_1)
            val result: Endpoints = jacksonObjectMapper().readValue(rsp["body"] as String)

            assertEquals(expected.sortedBy { it.apiRef }, result.endpoints.sortedBy { it.apiRef })
        }

        @Test
        fun filterByServiceType() {
            val rsp = apiGet("/endpoints?environment=test&serviceType=kontoopplysninger", emptyMap(), port)
            assertEquals(HttpStatus.OK.value(), rsp["status"])

            val expected = listOf(TEST_0, TEST_2)
            val result: Endpoints = jacksonObjectMapper().readValue(rsp["body"] as String)

            assertEquals(expected.sortedBy { it.apiRef }, result.endpoints.sortedBy { it.apiRef })
        }

        @Test
        fun filterByOrgNumberAndServiceType() {
            val rsp = apiGet("/endpoints?environment=test&orgNos=987654321&serviceType=Kontoopplysninger", emptyMap(), port)
            assertEquals(HttpStatus.OK.value(), rsp["status"])

            val expected = Endpoints(listOf(TEST_0), 1)
            val result: Endpoints = jacksonObjectMapper().readValue(rsp["body"] as String)

            assertEquals(expected, result)
        }

        @Test
        fun badRequestForNonEnumValues() {
            val rspNull = apiGet("/endpoints", emptyMap(), port)
            val rspNumber = apiGet("/endpoints?environment=132", emptyMap(), port)
            val rspString = apiGet("/endpoints?environment=asd", emptyMap(), port)

            assertEquals(HttpStatus.BAD_REQUEST.value(), rspNull["status"])
            assertEquals(HttpStatus.BAD_REQUEST.value(), rspNumber["status"])
            assertEquals(HttpStatus.BAD_REQUEST.value(), rspString["status"])
        }

    }

}