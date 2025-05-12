package ukma.edu.ua.core

public actual fun getPlatformInfo(params: String): String {
    val osVersion = android.os.Build.VERSION.SDK_INT
    return "Android $osVersion"
}
