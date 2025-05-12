package ukma.edu.ua.shared.data

import kotlinx.serialization.json.Json

public object Serialization {
    public val json: Json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }
}
