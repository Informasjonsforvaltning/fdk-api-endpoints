package no.fdk.endpoints.unit

import no.fdk.endpoints.mapper.mapDataServicesRDFToEndpoints
import no.fdk.endpoints.model.Environment
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Tag("unit")
class Mappers {

    @Nested
    internal inner class OrgNumber {

        @Test
        fun fromURIResourceIdentifier() {
            val rdfData = """
                @prefix dct:   <http://purl.org/dc/terms/> .
                @prefix dcat:  <http://www.w3.org/ns/dcat#> .
                @prefix foaf:  <http://xmlns.com/foaf/0.1/> .

                <https://example.com/dataservices/1>
                    a                         dcat:DataService ;
                    dct:publisher             <https://example.com/organizations/1> .

                <https://example.com/organizations/1>
                    a               foaf:Agent ;
                    dct:identifier  "123456789" .
            """.trimIndent()

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION)

            assertEquals("123456789", result.first().orgNo)
        }

        @Test
        fun fromBlankNodeIdentifier() {
            val rdfData = """
                @prefix dct:   <http://purl.org/dc/terms/> .
                @prefix dcat:  <http://www.w3.org/ns/dcat#> .
                @prefix foaf:  <http://xmlns.com/foaf/0.1/> .

                <https://example.com/dataservices/1>
                    a                         dcat:DataService ;
                    dct:publisher             [ a               foaf:Agent ;
                                                dct:identifier  "123456789" ] .
            """.trimIndent()

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION)

            assertEquals("123456789", result.first().orgNo)
        }

        @Test
        fun fromResourceURI() {
            val rdfData = """
                @prefix dct:   <http://purl.org/dc/terms/> .
                @prefix dcat:  <http://www.w3.org/ns/dcat#> .
                @prefix foaf:  <http://xmlns.com/foaf/0.1/> .

                <https://example.com/dataservices/1>
                    a                         dcat:DataService ;
                    dct:publisher             <https://example.com/organizations/123456789> .
            """.trimIndent()

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION)

            assertEquals("123456789", result.first().orgNo)
        }

        @Test
        fun fromLiteralURI() {
            val rdfData = """
                @prefix dct:   <http://purl.org/dc/terms/> .
                @prefix dcat:  <http://www.w3.org/ns/dcat#> .
                @prefix foaf:  <http://xmlns.com/foaf/0.1/> .

                <https://example.com/dataservices/1>
                    a                         dcat:DataService ;
                    dct:publisher             "https://example.com/organizations/123456789" .
            """.trimIndent()

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION)

            assertEquals("123456789", result.first().orgNo)
        }

        @Test
        fun noPublisherIdFoundInURI() {
            val rdfData = """
                @prefix dct:   <http://purl.org/dc/terms/> .
                @prefix dcat:  <http://www.w3.org/ns/dcat#> .
                @prefix foaf:  <http://xmlns.com/foaf/0.1/> .

                <https://example.com/dataservices/1>
                    a                         dcat:DataService ;
                    dct:publisher             <https://example.com/organizations/1> .
            """.trimIndent()

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION)

            assertEquals(null, result.first().orgNo)
        }

        @Test
        fun handlesMissingPublisher() {
            val rdfData = """
                @prefix dct:   <http://purl.org/dc/terms/> .
                @prefix dcat:  <http://www.w3.org/ns/dcat#> .
                @prefix foaf:  <http://xmlns.com/foaf/0.1/> .

                <https://example.com/dataservices/1>
                    a                         dcat:DataService .
            """.trimIndent()

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION)

            assertEquals(null, result.first().orgNo)
        }

    }

}