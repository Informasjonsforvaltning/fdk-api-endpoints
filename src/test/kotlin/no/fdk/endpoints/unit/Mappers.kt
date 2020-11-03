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

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION, "https://example.com")

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

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION, "https://example.com")

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

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION, "https://example.com")

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

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION, "https://example.com")

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

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION, "https://example.com")

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

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION, "https://example.com")

            assertEquals(null, result.first().orgNo)
        }

    }

    @Nested
    internal inner class ApiRef {

        @Test
        fun identifierIsExtracted() {
            val rdfData = """
                @prefix dct:   <http://purl.org/dc/terms/> .
                @prefix dcat:  <http://www.w3.org/ns/dcat#> .
                @prefix foaf:  <http://xmlns.com/foaf/0.1/> .

                <https://example.com/dataservices/1>
                    a                         dcat:DataService .

                <https://example.com/metadata/1>
                    a                   dcat:CatalogRecord ;
                    dct:identifier      "fdk-id" ;
                    foaf:primaryTopic   <https://example.com/dataservices/1> .
            """.trimIndent()

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION, "https://fdk-uri.com/dataservices")

            assertEquals("https://fdk-uri.com/dataservices/fdk-id", result.first().apiRef)
        }

        @Test
        fun handlesMissingMetaData() {
            val rdfData = """
                @prefix dct:   <http://purl.org/dc/terms/> .
                @prefix dcat:  <http://www.w3.org/ns/dcat#> .
                @prefix foaf:  <http://xmlns.com/foaf/0.1/> .

                <https://example.com/dataservices/1>
                    a                         dcat:DataService .
            """.trimIndent()

            val result = mapDataServicesRDFToEndpoints(rdfData, Environment.PRODUCTION, "https://fdk-uri.com/dataservices")

            assertEquals(null, result.first().apiRef)
        }

    }

}