package no.fdk.endpoints.model

data class Endpoint(
    val apiRef: String?,
    val orgNo: String?,
    val serviceType: String?,
    val url: String?,
    val transportProfile: String?,
    val environment: Environment?
)

enum class Environment {
    TEST,
    PRODUCTION
}