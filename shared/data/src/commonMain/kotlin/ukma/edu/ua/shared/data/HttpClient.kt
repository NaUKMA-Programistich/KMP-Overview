package ukma.edu.ua.shared.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

public fun createHttpClient(): HttpClient = HttpClient(httpEngine()) {
    install(ContentNegotiation) {
        json(Serialization.json)
    }
    install(Logging) {
        logger = object : io.ktor.client.plugins.logging.Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }
    defaultRequest {
        host = "192.168.8.184"
        port = 8080
        contentType(ContentType.Application.Json)
    }
}

internal expect fun httpEngine(): HttpClientEngine
