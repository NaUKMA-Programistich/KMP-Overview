package ukma.edu.ua.core

import platform.Foundation.NSProcessInfo

public actual fun getPlatformInfo(params: String): String {
    val osVersion = NSProcessInfo.processInfo.operatingSystemVersionString.split(" ")[1]
    return "$params MacOS $osVersion"
}
