package ukma.edu.ua.core

public actual fun getPlatformInfo(params: String): String {
    val osVersion = System.getProperty("java.version")
    return "Desktop JVM $osVersion"
}
