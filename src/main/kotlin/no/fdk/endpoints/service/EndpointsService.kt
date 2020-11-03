package no.fdk.endpoints.service

import no.fdk.endpoints.adapter.DataServiceHarvesterAdapter
import no.fdk.endpoints.config.ApplicationProperties
import no.fdk.endpoints.mapper.mapDataServicesRDFToEndpoints
import no.fdk.endpoints.model.Endpoint
import no.fdk.endpoints.model.Environment
import org.springframework.stereotype.Service

@Service
class EndpointsService(
    private val applicationProperties: ApplicationProperties,
    private val adapter: DataServiceHarvesterAdapter
) {

    fun searchForDataServiceEndpoints(requestedEnvironment: Environment, requestedServiceType: String?, orgNos: List<String>?): List<Endpoint> {
        val currentEnvironment = when(applicationProperties.namespace) {
            "production" -> Environment.PRODUCTION
            "demo" -> Environment.TEST
            "staging" -> Environment.TEST
            else -> Environment.PRODUCTION
        }

        return if(currentEnvironment == requestedEnvironment) {
            adapter.getDataServices()
                ?.let { mapDataServicesRDFToEndpoints(it, currentEnvironment, applicationProperties.fdkDataServiceUri) }
                ?.filter { it.hasCorrectServiceType(requestedServiceType) }
                ?.filter { orgNos?.contains(it.orgNo) ?: true }
                ?: emptyList()
        } else emptyList()
    }

}

private fun Endpoint.hasCorrectServiceType(requestedServiceType: String?): Boolean =
    if (requestedServiceType != null) serviceType?.toLowerCase() == requestedServiceType.toLowerCase()
    else true
