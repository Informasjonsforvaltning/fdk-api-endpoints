package no.fdk.endpoints.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application")
data class ApplicationProperties(
    val dataServiceCatalogsUri: String,
    val namespace: String,
    val fdkDataServiceUri: String
)
