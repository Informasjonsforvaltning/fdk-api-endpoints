package no.fdk.endpoints

import no.fdk.endpoints.config.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.SpringApplication

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
