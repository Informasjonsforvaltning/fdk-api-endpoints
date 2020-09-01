package no.fdk.endpoints.model

import java.time.LocalDate

data class Endpoint(
    val apiRef: String,
    val orgNo: String,
    val serviceType: String,
    val url: String,
    val activationDate: LocalDate,
    val expirationDate: LocalDate,
    val transportProfile: String,
    val environment: Environment
)

enum class Environment {
    test,
    production
}