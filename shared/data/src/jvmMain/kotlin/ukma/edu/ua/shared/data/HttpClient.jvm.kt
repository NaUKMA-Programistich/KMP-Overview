package ukma.edu.ua.shared.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun httpEngine(): HttpClientEngine = OkHttp.create()
