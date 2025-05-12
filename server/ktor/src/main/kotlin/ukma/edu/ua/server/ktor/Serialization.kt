package ukma.edu.ua.server.ktor

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import ukma.edu.ua.shared.data.Serialization

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Serialization.json)
    }
}