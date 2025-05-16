package ukma.edu.ua.shared.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js

internal actual fun httpEngine(): HttpClientEngine = Js.create()
