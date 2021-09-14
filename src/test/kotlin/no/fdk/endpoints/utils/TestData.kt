package no.fdk.endpoints.utils

import no.fdk.endpoints.model.Endpoint
import no.fdk.endpoints.model.Environment

const val LOCAL_SERVER_PORT = 5000

val PROD_0 = Endpoint(
    apiRef = "https://staging.fellesdatakatalog.digdir.no/dataservices/db38e789-51cc-3ff4-b55a-6ec94f6f711d",
    orgNo = "987654321",
    serviceType = "Kontoopplysninger",
    url = "https://example.com/both-account-details-publisher/v1",
    transportProfile = "eOppslag",
    environment = Environment.production)
val PROD_1 = Endpoint(
    apiRef = "https://staging.fellesdatakatalog.digdir.no/dataservices/495df11b-3dc1-3aa5-b74a-ce6dbe9344cd",
    orgNo = "123456789",
    serviceType = null,
    url = "https://example.com/not-account-deatils/v1",
    transportProfile = "eOppslag",
    environment = Environment.production)
val PROD_2 = Endpoint(
    apiRef = "https://staging.fellesdatakatalog.digdir.no/dataservices/afe46424-40ad-31fb-a992-8825bd40ab57",
    orgNo = null,
    serviceType = "Kontoopplysninger",
    url = "https://example.com/no-publisher/v1",
    transportProfile = "eOppslag",
    environment = Environment.production)
val PROD_3 = Endpoint(
    apiRef = "https://staging.fellesdatakatalog.digdir.no/dataservices/ac38e789-40ad-3ff4-b55a-8825bd40ab57",
    orgNo = "987654321",
    serviceType = "Kontoopplysninger",
    url = "https://example.com/new-account-details-publisher/v1",
    transportProfile = "eOppslag",
    environment = Environment.production)

val TEST_0 = Endpoint(
    apiRef = "https://staging.fellesdatakatalog.digdir.no/dataservices/db38e789-51cc-3ff4-b55a-6ec94f6f711d",
    orgNo = "987654321",
    serviceType = "Kontoopplysninger",
    url = "https://example.com/both-account-details-publisher/v1",
    transportProfile = "eOppslag",
    environment = Environment.test)
val TEST_1 = Endpoint(
    apiRef = "https://staging.fellesdatakatalog.digdir.no/dataservices/495df11b-3dc1-3aa5-b74a-ce6dbe9344cd",
    orgNo = "123456789",
    serviceType = null,
    url = "https://example.com/not-account-deatils/v1",
    transportProfile = "eOppslag",
    environment = Environment.test)
val TEST_2 = Endpoint(
    apiRef = "https://staging.fellesdatakatalog.digdir.no/dataservices/afe46424-40ad-31fb-a992-8825bd40ab57",
    orgNo = null,
    serviceType = "Kontoopplysninger",
    url = "https://example.com/no-publisher/v1",
    transportProfile = "eOppslag",
    environment = Environment.test)
val TEST_3 = Endpoint(
    apiRef = "https://staging.fellesdatakatalog.digdir.no/dataservices/ac38e789-40ad-3ff4-b55a-8825bd40ab57",
    orgNo = "987654321",
    serviceType = "Kontoopplysninger",
    url = "https://example.com/new-account-details-publisher/v1",
    transportProfile = "eOppslag",
    environment = Environment.test)
