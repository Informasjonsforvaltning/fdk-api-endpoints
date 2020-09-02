package no.fdk.endpoints.service

import no.fdk.endpoints.adapter.DataServiceHarvesterAdapter
import no.fdk.endpoints.model.Endpoint
import no.fdk.endpoints.model.Environment
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class EndpointsService(private val adapter: DataServiceHarvesterAdapter) {

    fun searchForDataServiceEndpoints(environment: Environment, activeOnly: Boolean, serviceType: String?, orgNos: List<String>?): List<Endpoint> {
        val dataServices = adapter.getDataServices()
        // TODO: map data services to endpoints
        val endpoints: List<Endpoint> = emptyList()

        return endpoints
            .filter { it.environment == environment }
            .filter { if (activeOnly) it.isActive() else true }
            .filter { if (serviceType != null) it.serviceType == serviceType else true }
            .filter { orgNos?.contains(it.orgNo) ?: true }
    }

}

private fun Endpoint.isActive(): Boolean {
    val now = LocalDate.now()
    return when {
        expirationDate != null && now.isAfter(expirationDate) -> false
        activationDate != null && now.isBefore(activationDate) -> false
        else -> true
    }
}