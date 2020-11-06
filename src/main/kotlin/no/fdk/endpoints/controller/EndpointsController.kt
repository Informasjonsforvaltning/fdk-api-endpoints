package no.fdk.endpoints.controller

import no.fdk.endpoints.model.Endpoints
import no.fdk.endpoints.model.Environment
import no.fdk.endpoints.service.EndpointsService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val logger = LoggerFactory.getLogger(EndpointsController::class.java)

@CrossOrigin
@RestController
@RequestMapping(value = ["/endpoints"])
class EndpointsController(private val service: EndpointsService) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun searchAPICatalog(
        @RequestParam(value = "environment", required = true) environment: String,
        @RequestParam(value = "activeOnly", required = false, defaultValue = "true") activeOnly: Boolean,
        @RequestParam(value = "serviceType", required = false) serviceType: String?,
        @RequestParam(value = "orgNos", required = false) orgNos: List<String>?
    ): ResponseEntity<Endpoints> =
        try {
            val env = Environment.valueOf(environment.toLowerCase())
            val endpoints = service.searchForDataServiceEndpoints(env, serviceType, orgNos)
            ResponseEntity(Endpoints(endpoints), HttpStatus.OK)
        } catch (envError: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }

}