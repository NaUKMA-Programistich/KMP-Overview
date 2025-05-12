package ukma.edu.ua.shared.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

internal actual fun httpEngine(): HttpClientEngine = Darwin.create()
