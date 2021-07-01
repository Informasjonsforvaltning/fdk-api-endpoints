package no.fdk.endpoints.adapter

import no.fdk.endpoints.config.ApplicationProperties
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

private val logger = LoggerFactory.getLogger(DataServiceHarvesterAdapter::class.java)

@Service
class DataServiceHarvesterAdapter(private val applicationProperties: ApplicationProperties) {

    fun getDataServices(): String? =
        try {
            val connection = URL(applicationProperties.dataServiceCatalogsUri).openConnection() as HttpURLConnection
            connection.setRequestProperty("Accept", "text/turtle")

            if (connection.responseCode != HttpStatus.OK.value()) {
                logger.error("Download from harvester failed, statusCode: ${connection.responseCode}", Exception("Download from harvester failed, statusCode: ${connection.responseCode}"))
                null
            } else {
                connection
                    .inputStream
                    .bufferedReader()
                    .use(BufferedReader::readText)
            }

        } catch (ex: Exception) {
            logger.error("Error when downloading from harvester", ex)
            null
        }

}
