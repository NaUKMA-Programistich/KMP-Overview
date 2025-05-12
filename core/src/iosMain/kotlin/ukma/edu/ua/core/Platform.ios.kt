package ukma.edu.ua.core

import platform.UIKit.UIDevice

public actual fun getPlatformInfo(params: String): String {
    val osVersion = UIDevice.currentDevice.systemVersion
    return "$params iOS $osVersion"
}
