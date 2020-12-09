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
            "production" -> Environment.production
            "demo" -> Environment.test
            "staging" -> Environment.test
            else -> Environment.production
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
    if (requestedServiceType != null) serviceType.equals(requestedServiceType, ignoreCase = true)
    else true
