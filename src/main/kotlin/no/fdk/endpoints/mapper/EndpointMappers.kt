package no.fdk.endpoints.mapper

import no.fdk.endpoints.Application
import no.fdk.endpoints.model.Endpoint
import no.fdk.endpoints.model.Environment
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.rdf.model.Property
import org.apache.jena.rdf.model.RDFNode
import org.apache.jena.rdf.model.Resource
import org.apache.jena.vocabulary.DCAT
import org.apache.jena.vocabulary.DCTerms
import org.apache.jena.vocabulary.RDF
import org.slf4j.LoggerFactory
import java.io.StringReader

private val logger = LoggerFactory.getLogger(Application::class.java)
private const val BACKUP_BASE_URI = "http://example.com/"

private const val ACCOUNT_DETAILS_URI = "https://data.norge.no/def/serviceType#ACCOUNT_DETAILS"
private const val KONTOOPPLYSNINGER_URI = "https://data.norge.no/specification/kontoopplysninger"
private const val ACCOUNT_DETAILS_STRING_VALUE = "Kontoopplysninger"
private const val TRANSPORT_PROFILE = "eOppslag"

fun mapDataServicesRDFToEndpoints(rdfData: String, environment: Environment): List<Endpoint> {
    val model = ModelFactory.createDefaultModel()

    try {
        model.read(StringReader(rdfData), BACKUP_BASE_URI, "TURTLE")
    } catch (ex: Exception) {
        logger.error("Failed to parse data services", ex)
        return emptyList()
    }

    val endpoints: MutableList<Endpoint> = mutableListOf()

    model.listResourcesWithProperty(RDF.type, DCAT.DataService)
        ?.forEach {
            endpoints.add(Endpoint(
                apiRef = it.uriResourceAsString(DCAT.endpointDescription),
                orgNo = it.publisherId(),
                serviceType = it.serviceType(),
                url = it.uriResourceAsString(DCAT.endpointURL),
                transportProfile = TRANSPORT_PROFILE,
                environment = environment
            ))
        }

    return endpoints.toList()

}

private fun Resource.uriResourceAsString(property: Property): String? =
    listProperties(property)
        .toList()
        .firstOrNull()
        ?.resource
        ?.uri

private fun Resource.publisherId(): String? =
    listProperties(DCTerms.publisher)
        .toList()
        .firstOrNull()
        ?.`object`
        ?.let {
            if (it.isResource && it.asResource().hasProperty(DCTerms.identifier)) it.extractIdentifierValue()
            else it.extractPublisherIdFromURI()
        }

private fun Resource.serviceType(): String? =
    when (uriResourceAsString(DCTerms.conformsTo)) {
        ACCOUNT_DETAILS_URI -> ACCOUNT_DETAILS_STRING_VALUE
        KONTOOPPLYSNINGER_URI -> ACCOUNT_DETAILS_STRING_VALUE
        else -> null
    }

private fun RDFNode.extractIdentifierValue(): String? =
    asResource()
        ?.listProperties(DCTerms.identifier)
        ?.toList()
        ?.firstOrNull()
        ?.string

private fun RDFNode.extractPublisherIdFromURI(): String? {
    val uri = when {
        isURIResource -> asResource().uri
        isLiteral -> asLiteral().string
        else -> ""
    }

    val regex = Regex("""[0-9]{9}""")
    val allMatching = regex.findAll(uri).toList()

    return if (allMatching.size == 1) allMatching.first().value
    else null
}
