package ukma.edu.ua.core

public data class Platform(
    val name: String
)

public expect fun getPlatformInfo(params: String): String
