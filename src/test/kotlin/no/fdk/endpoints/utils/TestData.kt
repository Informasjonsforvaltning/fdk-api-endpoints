package no.fdk.endpoints.utils

import no.fdk.endpoints.model.Endpoint
import no.fdk.endpoints.model.Environment

const val LOCAL_SERVER_PORT = 5000

val PROD_0 = Endpoint(
    apiRef = "https://example.com/specs/BOTH_ACCOUNT_DETAILS_PUBLISHER.json",
    orgNo = "987654321",
    serviceType = "Kontoopplysninger",
    url = "https://example.com/both-account-details-publisher/v1",
    transportProfile = "eOppslag",
    environment = Environment.PRODUCTION)
val PROD_1 = Endpoint(
    apiRef = "https://example.com/specs/NOT_ACCOUNT_DETAILS.json",
    orgNo = "123456789",
    serviceType = null,
    url = "https://example.com/not-account-deatils/v1",
    transportProfile = "eOppslag",
    environment = Environment.PRODUCTION)
val PROD_2 = Endpoint(
    apiRef = "https://example.com/specs/NO_PUBLISHER.json",
    orgNo = null,
    serviceType = "Kontoopplysninger",
    url = "https://example.com/no-publisher/v1",
    transportProfile = "eOppslag",
    environment = Environment.PRODUCTION)

val TEST_0 = Endpoint(
    apiRef = "https://example.com/specs/BOTH_ACCOUNT_DETAILS_PUBLISHER.json",
    orgNo = "987654321",
    serviceType = "Kontoopplysninger",
    url = "https://example.com/both-account-details-publisher/v1",
    transportProfile = "eOppslag",
    environment = Environment.TEST)
val TEST_1 = Endpoint(
    apiRef = "https://example.com/specs/NOT_ACCOUNT_DETAILS.json",
    orgNo = "123456789",
    serviceType = null,
    url = "https://example.com/not-account-deatils/v1",
    transportProfile = "eOppslag",
    environment = Environment.TEST)
val TEST_2 = Endpoint(
    apiRef = "https://example.com/specs/NO_PUBLISHER.json",
    orgNo = null,
    serviceType = "Kontoopplysninger",
    url = "https://example.com/no-publisher/v1",
    transportProfile = "eOppslag",
    environment = Environment.TEST)
